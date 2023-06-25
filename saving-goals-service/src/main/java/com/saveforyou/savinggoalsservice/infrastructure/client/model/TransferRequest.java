package com.saveforyou.savinggoalsservice.infrastructure.client.model;

import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TransferRequest {

    private BigDecimal amount;
    private RecipientRequest recipient;

    public static TransferRequest from(SavingGoal savingGoal, BigDecimal amount){
        return TransferRequest.builder()
                .amount(amount)
                .recipient(RecipientRequest.builder()
                        .type("SAVING_GOAL")
                        .id(savingGoal.getId())
                        .description(savingGoal.getName())
                        .build())
                .build();
    }
}
