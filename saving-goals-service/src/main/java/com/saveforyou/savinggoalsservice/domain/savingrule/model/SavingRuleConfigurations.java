package com.saveforyou.savinggoalsservice.domain.savingrule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingRuleConfigurations {

    private boolean requiredExtraConfigurations;
    private boolean requiredDepositValueType;
    private boolean requiredDepositValue;
    private boolean requiredInitialAmount;
    private boolean requiredIncrementAmount;
    private boolean requiredLimitAmount;
    private boolean requiredCardType;
    private boolean requiredStartDate;
    private boolean requiredEndDate;
    private boolean requiredFrequency;
    private TransactionOrigin transactionOrigin;

    public enum TransactionOrigin {
        BANK_ACCOUNT, CARD_PAYMENTS, SAVING_GOAL
    }
}
