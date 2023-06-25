package com.saveforyou.savinggoalsservice.domain.savinggoal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoalDetails {

    private UUID id;
    private List<SavingRuleActive> savingRulesActives;
    private List<SavingRulesScheduling> savingRulesScheduling;
}

