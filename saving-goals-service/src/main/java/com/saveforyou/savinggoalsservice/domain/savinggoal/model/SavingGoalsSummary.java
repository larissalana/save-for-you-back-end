package com.saveforyou.savinggoalsservice.domain.savinggoal.model;

import com.saveforyou.savinggoalsservice.domain.commons.model.PaginationInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SavingGoalsSummary {

    private List<SavingGoal> savingGoals;
    private SavingGoalsSummaryInformation summaryInformation;
    private PaginationInfo paginationInfo;
}