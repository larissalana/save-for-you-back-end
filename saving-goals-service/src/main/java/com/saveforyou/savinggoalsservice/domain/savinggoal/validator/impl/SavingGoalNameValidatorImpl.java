package com.saveforyou.savinggoalsservice.domain.savinggoal.validator.impl;

import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savinggoal.validator.SavingGoalValidator;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingGoalNameValidatorImpl implements SavingGoalValidator {

    private static final String SAVING_GOAL_NAME_ALREADY_EXISTS = "Já existe um objetivo financeiro com o nome informado.";
    private final SavingGoalRepository savingGoalRepository;

    @Override
    public void validate(UUID clientId, SavingGoal savingGoal) {
        if(isNameAlreadyExists(clientId, savingGoal.getName())){
            throw new BadRequestException(SAVING_GOAL_NAME_ALREADY_EXISTS);
        }
    }

    private boolean isNameAlreadyExists(UUID clientId, String name) {
        return savingGoalRepository.existsByNameIgnoreCaseAndClientId(name, clientId);
    }
}
