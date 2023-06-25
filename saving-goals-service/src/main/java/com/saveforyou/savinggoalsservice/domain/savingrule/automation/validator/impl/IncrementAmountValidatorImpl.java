package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.saveforyou.savinggoalsservice.application.utils.ValidatorUtils.isAmountInvalid;

@Component
public class IncrementAmountValidatorImpl implements SavingRuleAutomationValidator {

    private static final String INCREMENT_AMOUNT_NULL = "O valor incremental do depósito precisa ser informado.";
    private static final String INCREMENT_AMOUNT_INVALID = "O valor incremental do depósito deve ser maior ou igual a 0,10.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if (Objects.isNull(depositConfigurations.getIncrementAmount())) {
            throw new BadRequestException(INCREMENT_AMOUNT_NULL);
        } else if (isAmountInvalid(depositConfigurations.getIncrementAmount())) {
            throw new BadRequestException(INCREMENT_AMOUNT_INVALID);
        }

    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && (savingRule.getConfigurations().isRequiredIncrementAmount());
    }
}