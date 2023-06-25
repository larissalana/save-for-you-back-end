package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DepositValueTypeValidatorImpl implements SavingRuleAutomationValidator {

    private static final String DEPOSIT_TYPE_NULL = "O tipo do depósito precisa ser informado.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if(Objects.isNull(depositConfigurations.getDepositValueType())){
            throw new BadRequestException(DEPOSIT_TYPE_NULL);
        }
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && savingRule.getConfigurations().isRequiredDepositValueType();
    }
}