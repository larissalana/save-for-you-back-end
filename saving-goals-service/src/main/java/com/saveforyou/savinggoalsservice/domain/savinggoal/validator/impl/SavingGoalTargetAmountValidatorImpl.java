package com.saveforyou.savinggoalsservice.domain.savinggoal.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savinggoal.validator.SavingGoalValidator;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.saveforyou.savinggoalsservice.application.utils.ValidatorUtils.isAmountInvalid;

@Component
public class SavingGoalTargetAmountValidatorImpl implements SavingGoalValidator {

    private static final String TARGET_AMOUNT_INVALID = "O valor alvo precisa ser maior ou igual a 0,10.";

    @Override
    public void validate(UUID clientId, SavingGoal savingGoal) {
        if (isAmountInvalid(savingGoal.getTargetAmount())) {
            throw new BadRequestException(TARGET_AMOUNT_INVALID);
        }
    }
}