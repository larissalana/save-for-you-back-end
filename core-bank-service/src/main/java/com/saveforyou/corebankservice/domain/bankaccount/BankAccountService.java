package com.saveforyou.corebankservice.domain.bankaccount;

import com.saveforyou.corebankservice.application.enums.TransactionType;
import com.saveforyou.corebankservice.application.exceptions.NoSuchElementFoundException;
import com.saveforyou.corebankservice.application.utils.AccountNumberGenerator;
import com.saveforyou.corebankservice.domain.bankaccount.mapper.BankAccountMapper;
import com.saveforyou.corebankservice.domain.bankaccount.model.BankAccount;
import com.saveforyou.corebankservice.domain.bankaccount.validator.OpenBankAccountValidator;
import com.saveforyou.corebankservice.domain.client.ClientService;
import com.saveforyou.corebankservice.domain.client.model.Client;
import com.saveforyou.corebankservice.domain.savinggoal.SavingGoalService;
import com.saveforyou.corebankservice.domain.transaction.model.Recipient;
import com.saveforyou.corebankservice.domain.transaction.model.Transaction;
import com.saveforyou.corebankservice.domain.transaction.service.TransactionService;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.BankAccountRepository;
import io.swagger.model.DepositApiModel;
import io.swagger.model.OpenBankAccountRequest;
import io.swagger.model.OpenBankAccountResponse;
import io.swagger.model.TransferInternalApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.swagger.model.DepositTypeApiModel.SALARY_DEPOSIT;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private static final String BANK_ACCOUNT_NOT_FOUND_CLIENT = "Nenhuma conta bancária foi encontrada para o cliente informado.";
    private static final String BANK_ACCOUNT_NOT_FOUND = "Nenhuma conta bancária foi encontrada para os parâmetros informados.";

    private final BankAccountMapper bankAccountMapper;
    private final BankAccountRepository bankAccountRepository;
    private final ClientService clientService;
    private final TransactionService transactionService;
    private final SavingGoalService savingGoalService;

    private final List<OpenBankAccountValidator> validators;

    public OpenBankAccountResponse openBankAccount(OpenBankAccountRequest openBankAccountRequest) {
        validateRequest(openBankAccountRequest);
        var clientResponse = clientService.create(openBankAccountRequest);
        var bankAccount = create(clientResponse);

        return bankAccountMapper.toResponse(bankAccount);
    }

    public BankAccount getByBranchAndAccountNumber(String branch, String accountNumber) {
        var bankAccountEntity = bankAccountRepository.findByBranchAndAccountNumber(branch, accountNumber)
                .orElseThrow(() -> new NoSuchElementFoundException(BANK_ACCOUNT_NOT_FOUND));

        return bankAccountMapper.toDomainModel(bankAccountEntity);
    }

    public BankAccount getByClientId(UUID clientId) {
        var bankAccountEntity = bankAccountRepository.findByClientId(clientId)
                .orElseThrow(() -> new NoSuchElementFoundException(BANK_ACCOUNT_NOT_FOUND_CLIENT));

        return bankAccountMapper.toDomainModel(bankAccountEntity);
    }

    public BankAccount deposit(String branch, String accountNumber, DepositApiModel depositApiModel) {
        var bankAccount = getByBranchAndAccountNumber(branch, accountNumber);
        bankAccount.deposit(depositApiModel.getAmount());
        var bankAccountUpdated = bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));
        var transaction = Transaction.from(bankAccount, depositApiModel);

        transactionService.create(transaction);

        if (SALARY_DEPOSIT.equals(depositApiModel.getType()))
            savingGoalService.transactionNotification(bankAccount.getClient().getId(), transaction.getAmount(), transaction.getType().toString());

        return bankAccountMapper.toDomainModel(bankAccountUpdated);
    }

    public void transferInternal(UUID clientId, TransferInternalApiModel transferInternalApiModel) {
        var bankAccount = getByClientId(clientId);
        bankAccount.transfer(transferInternalApiModel.getAmount());
        var bankAccountToUpdated = bankAccountMapper.toEntity(bankAccount);
        var recipient = Recipient.from(transferInternalApiModel.getRecipient());
        var transactionTransfer = Transaction.from(TransactionType.TRANSFER, bankAccountToUpdated.getClient(), recipient, transferInternalApiModel.getAmount());
        var transactionAutomation = Transaction.from(TransactionType.AUTOMATION, bankAccountToUpdated.getClient(), recipient, transferInternalApiModel.getAmount());

        bankAccountRepository.save(bankAccountToUpdated);
        transactionService.create(transactionTransfer);
        transactionService.create(transactionAutomation);
    }

    private BankAccount create(Client client) {
        var accountNumber = AccountNumberGenerator.generate();
        var bankAccount = bankAccountMapper.toCreate(accountNumber, client);
        var bankAccountSaved = bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));

        return bankAccountMapper.toDomainModel(bankAccountSaved);
    }

    private void validateRequest(OpenBankAccountRequest openBankAccountRequest) {
        validators.forEach(validator -> validator.validate(openBankAccountRequest));
    }
}