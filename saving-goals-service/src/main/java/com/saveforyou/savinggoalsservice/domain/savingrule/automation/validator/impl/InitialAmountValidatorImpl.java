package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.saveforyou.savinggoalsservice.application.utils.ValidatorUtils.isAmountInvalid;

@Component
public class InitialAmountValidatorImpl implements SavingRuleAutomationValidator {

    private static final String INITIAL_AMOUNT_NULL = "O valor inicial do depósito precisa ser informado.";
    private static final String INITIAL_AMOUNT_INVALID = "O valor inicial do depósito deve ser maior ou igual a 0,10.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if (Objects.isNull(depositConfigurations.getInitialAmount())) {
            throw new BadRequestException(INITIAL_AMOUNT_NULL);
        } else if (isAmountInvalid(depositConfigurations.getInitialAmount())) {
            throw new BadRequestException(INITIAL_AMOUNT_INVALID);
        }
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && (savingRule.getConfigurations().isRequiredInitialAmount());
    }
}