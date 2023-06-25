package com.saveforyou.corebankservice.domain.card.model;

import com.saveforyou.corebankservice.application.enums.Status;
import com.saveforyou.corebankservice.domain.client.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private UUID id;
    private String number;
    private String holderName;
    private String expirationDate;
    private String securityCode;
    private String brand = "BRAND";
    private BigDecimal creditLimit;
    private BigDecimal balance;
    private Status status;
    private Client client;
    private LocalDateTime createdAt;

    public void charge(BigDecimal amount){
        /*if(this.balance.add(amount).compareTo(this.creditLimit) <= 0)
            throw new IllegalArgumentException("Exceeded credit limit");*/

        this.balance = this.balance.subtract(amount);
    }
}
