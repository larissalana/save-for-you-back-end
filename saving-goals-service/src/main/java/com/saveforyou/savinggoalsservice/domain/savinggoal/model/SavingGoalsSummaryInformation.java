package com.saveforyou.savinggoalsservice.domain.savinggoal.model;

import com.saveforyou.savinggoalsservice.application.utils.NumberUtils;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingGoalsSummaryInformation {

    private String timeLeftTotal;
    private BigDecimal savingAmountTotal;
    private BigDecimal targetAmountTotal;
    private BigDecimal percentageAlreadySavedTotal;

    public BigDecimal getPercentageAlreadySavedTotal() {
        return NumberUtils.percentage(this.savingAmountTotal, this.targetAmountTotal);
    }
}
