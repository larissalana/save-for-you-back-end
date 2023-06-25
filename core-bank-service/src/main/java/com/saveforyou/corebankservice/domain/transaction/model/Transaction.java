package com.saveforyou.corebankservice.domain.transaction.model;

import com.saveforyou.corebankservice.application.enums.RecipientType;
import com.saveforyou.corebankservice.application.enums.SenderType;
import com.saveforyou.corebankservice.application.enums.TransactionStatus;
import com.saveforyou.corebankservice.application.enums.TransactionType;
import com.saveforyou.corebankservice.domain.bankaccount.model.BankAccount;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.ClientEntity;
import io.swagger.model.DepositApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

import static io.swagger.model.DepositTypeApiModel.DEPOSIT;

@Getter
@Setter
@Builder
public class Transaction {

    private UUID id;
    private TransactionType type;
    private TransactionStatus status;
    private BigDecimal amount;
    private Recipient recipient;
    private Sender sender;

    public static final String SENDER_SALARY_DEPOSIT = "Empresa XPTO SA";
    public static final String SENDER_DEPOSIT = "Caixa Eletrônico";

    public static Transaction from(BankAccount bankAccount, DepositApiModel depositApiModel) {
        return Transaction.builder()
                .type(TransactionType.valueOf(depositApiModel.getType().name()))
                .status(TransactionStatus.PROCESSED)
                .amount(depositApiModel.getAmount())
                .recipient(Recipient.builder()
                        .type(RecipientType.ACCOUNT_INTERNAL)
                        .id(bankAccount.getClient().getId())
                        .description(bankAccount.getClient().getName())
                        .build())
                .sender(Sender.builder()
                        .type(SenderType.ACCOUNT_EXTERNAL)
                        .id(UUID.randomUUID())
                        .description(DEPOSIT.equals(depositApiModel.getType())
                                ? SENDER_DEPOSIT
                                : SENDER_SALARY_DEPOSIT)
                        .build())
                .build();
    }

    public static Transaction from(TransactionType transactionType, ClientEntity sender, Recipient recipient, BigDecimal amount){
        return Transaction.builder()
                .type(transactionType)
                .status(TransactionStatus.PROCESSED)
                .amount(amount)
                .recipient(recipient)
                .sender(Sender.builder()
                        .type(SenderType.ACCOUNT_INTERNAL)
                        .id(sender.getId())
                        .description(sender.getName())
                        .build())
                .build();
    }
}