package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.saveforyou.savinggoalsservice.application.enums.DepositValueType.AMOUNT;
import static com.saveforyou.savinggoalsservice.application.enums.DepositValueType.PERCENTAGE;
import static com.saveforyou.savinggoalsservice.application.utils.ValidatorUtils.isAmountInvalid;
import static com.saveforyou.savinggoalsservice.application.utils.ValidatorUtils.isPercentageInvalid;

@Component
public class DepositValueValidatorImpl implements SavingRuleAutomationValidator {

    private static final String AMOUNT_NULL = "O valor do depósito precisa ser informado.";
    private static final String AMOUNT_INVALID = "O valor do depósito deve ser maior ou igual a 0,10.";
    private static final String PERCENTAGE_NULL = "O percentual do depósito precisa ser informado.";
    private static final String PERCENTAGE_INVALID = "O percentual do depósito deve estar entre 1% a 20%.";


    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if(AMOUNT.equals(depositConfigurations.getDepositValueType())){
            validateAmount(depositConfigurations);
        } else if(PERCENTAGE.equals(depositConfigurations.getDepositValueType())){
            validatePercentage(depositConfigurations);
        }
    }

    private void validateAmount(DepositConfigurations depositConfigurations){
        if (Objects.isNull(depositConfigurations.getDepositValue())) {
            throw new BadRequestException(AMOUNT_NULL);
        } else if (isAmountInvalid(depositConfigurations.getDepositValue())) {
            throw new BadRequestException(AMOUNT_INVALID);
        }
    }

    private void validatePercentage(DepositConfigurations depositConfigurations){
        if (Objects.isNull(depositConfigurations.getDepositValue())) {
            throw new BadRequestException(PERCENTAGE_NULL);
        } else if (isPercentageInvalid(depositConfigurations.getDepositValue())) {
            throw new BadRequestException(PERCENTAGE_INVALID);
        }
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && (savingRule.getConfigurations().isRequiredDepositValue());
    }
}