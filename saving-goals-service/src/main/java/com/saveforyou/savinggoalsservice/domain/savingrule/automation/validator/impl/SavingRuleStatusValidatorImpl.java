package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Status.AVAILABLE_SOON;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Status.INACTIVE;

@Component
public class SavingRuleStatusValidatorImpl implements SavingRuleAutomationValidator {

    private static final String STATUS_INVALID = "Somente uma regra ativa pode ser habilitada.";

    @Override
    public void validate(DepositConfigurations depositConfigurations) {
        throw new BadRequestException(STATUS_INVALID);
    }

    @Override
    public boolean canValidate(SavingRule savingRule) {
        return Stream.of(INACTIVE, AVAILABLE_SOON).anyMatch(status -> status.equals(savingRule.getStatus()));
    }
}