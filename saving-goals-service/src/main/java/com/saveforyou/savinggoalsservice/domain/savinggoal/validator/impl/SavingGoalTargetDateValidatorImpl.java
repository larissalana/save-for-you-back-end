package com.saveforyou.savinggoalsservice.domain.savinggoal.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savinggoal.validator.SavingGoalValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class SavingGoalTargetDateValidatorImpl implements SavingGoalValidator {

    private static final String TARGET_DATE_LESS_THAN_TODAY = "A data alvo precisa ser maior ou igual a data atual.";

    @Override
    public void validate(UUID clientId, SavingGoal savingGoal) {
        if (isTargetDateLessThanToday(savingGoal)) {
            throw new BadRequestException(TARGET_DATE_LESS_THAN_TODAY);
        }
    }

    private boolean isTargetDateLessThanToday(SavingGoal savingGoal) {
        return savingGoal.getTargetDate().isBefore(LocalDate.now());
    }
}