package com.saveforyou.savinggoalsservice.domain.savingrule.processor;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;

public interface SavingRuleProcessor {

    boolean canProcess(SavingRule savingRule);

    void configureAndEnableAutomation(SavingRuleAutomation savingRuleAutomation, DepositConfigurations depositConfigurations);

    void disableAutomation(SavingRuleAutomation savingRuleAutomation);
}