package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StartDateValidatorImpl implements SavingRuleAutomationValidator {

    private static final String START_DATE_NULL = "A data inicial precisa ser informada.";
    private static final String START_DATE_GREATER_THAN_END_DATE = "A data inicial precisa ser menor que a data limite.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if (Objects.isNull(depositConfigurations.getStartDate())) {
            throw new BadRequestException(START_DATE_NULL);
        } else if (isStartDateGreaterThanEndDate(depositConfigurations)) {
            throw new BadRequestException(START_DATE_GREATER_THAN_END_DATE);
        }
    }

    private boolean isStartDateGreaterThanEndDate(DepositConfigurations depositConfigurations) {
        return Objects.nonNull(depositConfigurations.getEndDate())
                && depositConfigurations.getStartDate().isAfter(depositConfigurations.getEndDate());
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && savingRule.getConfigurations().isRequiredStartDate();
    }
}