package com.saveforyou.savinggoalsservice.domain.savingrule.scheduling;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.scheduling.mapper.SavingRuleSchedulingMapper;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingRuleSchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingRuleSchedulingService {

    private final SavingRuleSchedulingMapper savingRuleSchedulingMapper;
    private final SavingRuleSchedulingRepository savingRuleSchedulingRepository;

    public void createBy(SavingRuleAutomation savingRuleAutomation){
        var schedulingToCreate = savingRuleSchedulingMapper.toCreate(savingRuleAutomation);
        savingRuleSchedulingRepository.save(schedulingToCreate);
    }

    public void deleteAllPendingBy(SavingRuleAutomation savingRuleAutomation) {
        var savingGoalId = savingRuleAutomation.getSavingGoal().getId();
        var savingRuleId = savingRuleAutomation.getSavingRule().getId();

        savingRuleSchedulingRepository.deleteAllPendingBy(savingGoalId, savingRuleId);
    }
}
