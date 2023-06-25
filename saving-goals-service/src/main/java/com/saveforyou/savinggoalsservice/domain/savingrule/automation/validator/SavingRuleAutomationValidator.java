package com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;

public interface SavingRuleAutomationValidator {

    void validate(DepositConfigurations depositConfigurations);

    boolean canValidate(SavingRule savingRule);
}