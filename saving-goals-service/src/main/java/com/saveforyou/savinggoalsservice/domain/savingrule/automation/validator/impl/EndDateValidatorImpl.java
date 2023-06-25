package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EndDateValidatorImpl implements SavingRuleAutomationValidator {

    private static final String END_DATE_NULL = "A data final precisa ser informada.";
    private static final String END_DATE_LESS_THAN_START_DATE = "A data final precisa ser maior que a data inicial.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        if (Objects.isNull(depositConfigurations.getEndDate())) {
            throw new BadRequestException(END_DATE_NULL);
        } else if (isEndDateLessThanStartDate(depositConfigurations)) {
            throw new BadRequestException(END_DATE_LESS_THAN_START_DATE);
        }
    }

    private boolean isEndDateLessThanStartDate(DepositConfigurations depositConfigurations) {
        return Objects.nonNull(depositConfigurations.getStartDate())
                && depositConfigurations.getEndDate().isBefore(depositConfigurations.getStartDate());
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return savingRule.getConfigurations().isRequiredExtraConfigurations()
                && savingRule.getConfigurations().isRequiredEndDate();
    }
}