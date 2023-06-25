package com.saveforyou.savinggoalsservice.domain.savingrule.automation.model;

import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingRuleAutomation {

    private UUID id;
    private SavingGoal savingGoal;
    private SavingRule savingRule;
    private Status status;
    private Map<String, Object> configurations = new HashMap<>();

    public enum Status {
        ENABLED,
        DISABLED,
        PAUSED
    }

    public void addConfiguration(String key, Object value){
        this.configurations.put(key, value);
    }

    public void clearConfigurations(){
        this.configurations.clear();
    }
}
