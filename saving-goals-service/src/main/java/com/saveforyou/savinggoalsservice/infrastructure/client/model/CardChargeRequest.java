package com.saveforyou.savinggoalsservice.infrastructure.client.model;

import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CardChargeRequest {

    private String type;
    private BigDecimal amount;
    private RecipientRequest recipient;

    public static CardChargeRequest from(SavingGoal savingGoal, BigDecimal amount, String type){
        return CardChargeRequest.builder()
                .type(type)
                .amount(amount)
                .recipient(RecipientRequest.builder()
                        .type("SAVING_GOAL")
                        .id(savingGoal.getId())
                        .description(savingGoal.getName())
                        .build())
                .build();
    }
}
