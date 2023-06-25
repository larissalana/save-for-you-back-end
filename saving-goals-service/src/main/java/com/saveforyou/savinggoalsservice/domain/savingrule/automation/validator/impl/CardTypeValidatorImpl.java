package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CardTypeValidatorImpl implements SavingRuleAutomationValidator {

    private static final String CARD_TYPE_NULL = "A origem do depósito precisa ser informada.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if(Objects.isNull(depositConfigurations.getCardType())){
            throw new BadRequestException(CARD_TYPE_NULL);
        }
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && savingRule.getConfigurations().isRequiredCardType();
    }
}