package com.saveforyou.savinggoalsservice.domain.savinggoal.model;

import com.saveforyou.savinggoalsservice.application.utils.NumberUtils;
import com.saveforyou.savinggoalsservice.domain.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoal {

    private UUID id;
    private UUID clientId;
    private String name;
    private String icon;
    private Category category;
    private BigDecimal targetAmount;
    private LocalDate targetDate;
    private BigDecimal currentAmount;
    private LocalDate achievedDate;
    private Status status;
    private BigDecimal percentageAlreadySaved;

    public enum Status {
        IN_PROGRESS, PAUSED, ACHIEVED
    }

    public BigDecimal getPercentageAlreadySaved() {
        return NumberUtils.percentage(this.currentAmount, this.targetAmount);
    }
}