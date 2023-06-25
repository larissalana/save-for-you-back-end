package com.saveforyou.savinggoalsservice.domain.transaction.processor;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import io.swagger.model.TransactionNotificationApiModel;

public interface TransactionProcessor {
    
    boolean canProcess(SavingRule savingRule, TransactionNotificationApiModel transactionNotificationApiModel);

    Transaction process(SavingRuleAutomation savingRuleAutomation, TransactionNotificationApiModel transactionNotificationApiModel);
}
