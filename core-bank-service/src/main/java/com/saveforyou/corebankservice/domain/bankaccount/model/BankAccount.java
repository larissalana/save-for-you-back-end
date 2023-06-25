package com.saveforyou.corebankservice.domain.bankaccount.model;

import com.saveforyou.corebankservice.application.enums.Status;
import com.saveforyou.corebankservice.domain.client.model.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BankAccount {

    private UUID id;
    private String branch;
    private String accountNumber;
    private BigDecimal balance;
    private BankAccountType type;
    private Status status;
    private Client client;
    private LocalDateTime createdAt;

    public enum BankAccountType {
        CHECKING_ACCOUNT,
        SAVING_ACCOUNT,
        SALARY_ACCOUNT
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void deposit(BigDecimal amount){
        this.balance = this.balance.add(amount);
    }

    public void transfer(BigDecimal amount){
        if(this.balance.compareTo(amount) <= 0)
            throw new IllegalArgumentException("Insufficient balance");

        this.balance = this.balance.subtract(amount);
    }
}