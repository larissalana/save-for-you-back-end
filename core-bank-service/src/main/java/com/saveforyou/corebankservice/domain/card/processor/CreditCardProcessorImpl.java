package com.saveforyou.corebankservice.domain.card.processor;

import com.saveforyou.corebankservice.application.enums.TransactionType;
import com.saveforyou.corebankservice.application.exceptions.NoSuchElementFoundException;
import com.saveforyou.corebankservice.domain.savinggoal.SavingGoalService;
import com.saveforyou.corebankservice.domain.transaction.model.Recipient;
import com.saveforyou.corebankservice.domain.transaction.model.Transaction;
import com.saveforyou.corebankservice.domain.transaction.service.TransactionService;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.CardEntity;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.CardRepository;
import io.swagger.model.CardChargeApiModel;
import io.swagger.model.CardChargeInternalApiModel;
import io.swagger.model.ChargeTypeApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.saveforyou.corebankservice.application.utils.RandomRecipientUtils.generateRandomRecipient;
import static io.swagger.model.ChargeTypeApiModel.CREDIT;

@Service
@RequiredArgsConstructor
public class CreditCardProcessorImpl implements CardProcessor {

    private static final String CARD_NOT_FOUND_CLIENT = "Não foi encontrada cartão válido para o cliente informado.";

    private final CardRepository cardRepository;
    private final TransactionService transactionService;
    private final SavingGoalService savingGoalService;

    @Override
    public boolean canProcess(ChargeTypeApiModel chargeTypeApiModel) {
        return CREDIT.equals(chargeTypeApiModel);
    }

    @Override
    public void processCharge(UUID clientId, CardChargeApiModel cardChargeApiModel) {
        var card = updateCardBalance(clientId, cardChargeApiModel.getAmount());
        var transaction = Transaction.from(TransactionType.CREDIT_CHARGE, card.getClient(), generateRandomRecipient(), cardChargeApiModel.getAmount());

        transactionService.create(transaction);
        savingGoalService.transactionNotification(clientId, transaction.getAmount(), transaction.getType().toString());
    }

    @Override
    public void processChargeInternal(UUID clientId, CardChargeInternalApiModel cardChargeInternalApiModel) {
        var card = updateCardBalance(clientId, cardChargeInternalApiModel.getAmount());
        var recipient = Recipient.from(cardChargeInternalApiModel.getRecipient());
        var transactionCredit = Transaction.from(TransactionType.CREDIT_CHARGE, card.getClient(), recipient, cardChargeInternalApiModel.getAmount());
        var transactionAutomation = Transaction.from(TransactionType.AUTOMATION, card.getClient(), recipient, cardChargeInternalApiModel.getAmount());

        transactionService.create(transactionCredit);
        transactionService.create(transactionAutomation);
    }

    private CardEntity updateCardBalance(UUID clientId, BigDecimal amount){
        var card = cardRepository.findByClient_Id(clientId)
                .orElseThrow(() -> new NoSuchElementFoundException(CARD_NOT_FOUND_CLIENT));
        card.setBalance(card.getBalance().add(amount));

        return cardRepository.save(card);
    }
}
