package com.saveforyou.savinggoalsservice.domain.savingrule.processor.deposit;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.mapper.SavingRuleAutomationMapper;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.domain.savingrule.processor.SavingRuleProcessor;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingRuleAutomationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.saveforyou.savinggoalsservice.application.enums.DepositPropertiesKeys.*;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Category.DEPOSIT;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Status.ACTIVE;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Type.SALARY;

@Component
@RequiredArgsConstructor
public class SalaryDepositProcessorImpl implements SavingRuleProcessor {

    private final SavingRuleAutomationMapper savingRuleAutomationMapper;
    private final SavingRuleAutomationRepository savingRuleAutomationRepository;

    @Override
    public boolean canProcess(SavingRule savingRule) {
        return DEPOSIT.equals(savingRule.getCategory()) && SALARY.equals(savingRule.getType()) && ACTIVE.equals(savingRule.getStatus());
    }

    @Override
    public void configureAndEnableAutomation(SavingRuleAutomation savingRuleAutomation, DepositConfigurations depositConfigurations) {
        applyConfigurations(savingRuleAutomation, depositConfigurations);
        createOrUpdateAutomation(savingRuleAutomation);
    }

    @Override
    public void disableAutomation(SavingRuleAutomation savingRuleAutomation) {
        savingRuleAutomationRepository.deleteById(savingRuleAutomation.getId());
    }

    private void applyConfigurations(SavingRuleAutomation savingRuleAutomation, DepositConfigurations depositConfigurations) {
        savingRuleAutomation.clearConfigurations();
        savingRuleAutomation.addConfiguration(DEPOSIT_VALUE_TYPE.getKey(), depositConfigurations.getDepositValueType());
        savingRuleAutomation.addConfiguration(DEPOSIT_VALUE.getKey(), depositConfigurations.getDepositValue());
        savingRuleAutomation.addConfiguration(START_DATE.getKey(), depositConfigurations.getStartDate());
    }

    private void createOrUpdateAutomation(SavingRuleAutomation savingRuleAutomation) {
        var savingGoalId = savingRuleAutomation.getSavingGoal().getId();
        var savingRuleId = savingRuleAutomation.getSavingRule().getId();
        savingRuleAutomationRepository.findBySavingGoalAndSavingRule(savingGoalId, savingRuleId)
                .ifPresentOrElse(savingRuleAutomationDocument -> {
                    savingRuleAutomationDocument.setConfigurations(savingRuleAutomation.getConfigurations());
                    savingRuleAutomationRepository.save(savingRuleAutomationDocument);
                }, () -> {
                    savingRuleAutomationRepository.save(savingRuleAutomationMapper.toDocument(savingRuleAutomation));
                });
    }
}
