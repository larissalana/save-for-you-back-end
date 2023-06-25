package com.saveforyou.corebankservice.infrastructure.client.savinggoals.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransactionNotificationRequest {

    private String type;
    private BigDecimal amount;
}
