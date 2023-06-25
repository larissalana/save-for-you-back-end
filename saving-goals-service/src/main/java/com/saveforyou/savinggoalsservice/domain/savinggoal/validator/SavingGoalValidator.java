package com.saveforyou.savinggoalsservice.domain.savinggoal.validator;

import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;

import java.util.UUID;

public interface SavingGoalValidator {

    void validate(UUID clientId, SavingGoal savingGoal);
}