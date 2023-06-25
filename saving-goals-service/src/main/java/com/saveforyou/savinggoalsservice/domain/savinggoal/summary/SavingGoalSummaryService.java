package com.saveforyou.savinggoalsservice.domain.savinggoal.summary;

import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalsSummaryInformation;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.saveforyou.savinggoalsservice.application.utils.DateUtils.convertStringToLocalDate;
import static com.saveforyou.savinggoalsservice.application.utils.DateUtils.findDifferenceBetween;

@Service
@RequiredArgsConstructor
public class SavingGoalSummaryService {

    private final SavingGoalRepository savingGoalRepository;

    public SavingGoalsSummaryInformation getSummaryInfo(UUID clientId) {
        return SavingGoalsSummaryInformation.builder()
                .timeLeftTotal(getTimeMaxLeft(clientId))
                .savingAmountTotal(getSavingAmountTotal(clientId))
                .targetAmountTotal(getTargetAmountTotal(clientId))
                .build();
    }

    private String getTimeMaxLeft(UUID clientId) {
        return findDifferenceBetween(LocalDate.now(), convertStringToLocalDate(savingGoalRepository.maxTargetDate(clientId)));
    }

    private BigDecimal getSavingAmountTotal(UUID clientId) {
        return new BigDecimal(savingGoalRepository.sumCurrentAmount(clientId));
    }

    private BigDecimal getTargetAmountTotal(UUID clientId) {
        return new BigDecimal(savingGoalRepository.sumTargetAmount(clientId));
    }
}