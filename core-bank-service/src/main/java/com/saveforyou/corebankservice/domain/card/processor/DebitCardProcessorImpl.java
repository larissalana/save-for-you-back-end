package com.saveforyou.corebankservice.domain.card.processor;

import com.saveforyou.corebankservice.application.enums.TransactionType;
import com.saveforyou.corebankservice.application.exceptions.NoSuchElementFoundException;
import com.saveforyou.corebankservice.domain.savinggoal.SavingGoalService;
import com.saveforyou.corebankservice.domain.transaction.model.Recipient;
import com.saveforyou.corebankservice.domain.transaction.model.Transaction;
import com.saveforyou.corebankservice.domain.transaction.service.TransactionService;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.BankAccountEntity;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.BankAccountRepository;
import io.swagger.model.CardChargeApiModel;
import io.swagger.model.CardChargeInternalApiModel;
import io.swagger.model.ChargeTypeApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.saveforyou.corebankservice.application.utils.RandomRecipientUtils.generateRandomRecipient;
import static io.swagger.model.ChargeTypeApiModel.DEBIT;

@Service
@RequiredArgsConstructor
public class DebitCardProcessorImpl implements CardProcessor {

    private static final String BANK_ACCOUNT_NOT_FOUND_CLIENT = "Nenhuma conta bancária foi encontrada para o cliente informado.";

    private final BankAccountRepository bankAccountRepository;
    private final TransactionService transactionService;
    private final SavingGoalService savingGoalService;

    @Override
    public boolean canProcess(ChargeTypeApiModel chargeTypeApiModel) {
        return DEBIT.equals(chargeTypeApiModel);
    }

    @Override
    public void processCharge(UUID clientId, CardChargeApiModel cardChargeApiModel) {
        var bankAccount = updateBankAccountBalance(clientId, cardChargeApiModel.getAmount());
        var transaction = Transaction.from(TransactionType.DEBIT_CHARGE, bankAccount.getClient(), generateRandomRecipient(), cardChargeApiModel.getAmount());

        transactionService.create(transaction);
        savingGoalService.transactionNotification(clientId, transaction.getAmount(), transaction.getType().toString());
    }

    @Override
    public void processChargeInternal(UUID clientId, CardChargeInternalApiModel cardChargeApiModel) {
        var bankAccount = updateBankAccountBalance(clientId, cardChargeApiModel.getAmount());
        var recipient = Recipient.from(cardChargeApiModel.getRecipient());
        var transactionDebit = Transaction.from(TransactionType.DEBIT_CHARGE, bankAccount.getClient(), recipient, cardChargeApiModel.getAmount());
        var transactionAutomation = Transaction.from(TransactionType.AUTOMATION, bankAccount.getClient(), recipient, cardChargeApiModel.getAmount());

        transactionService.create(transactionDebit);
        transactionService.create(transactionAutomation);
    }

    private BankAccountEntity updateBankAccountBalance(UUID clientId, BigDecimal amount){
        var bankAccountEntity = bankAccountRepository.findByClientId(clientId)
                .orElseThrow(() -> new NoSuchElementFoundException(BANK_ACCOUNT_NOT_FOUND_CLIENT));
        bankAccountEntity.withdraw(amount);

        return bankAccountRepository.save(bankAccountEntity);
    }
}
