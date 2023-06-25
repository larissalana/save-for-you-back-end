package com.saveforyou.savinggoalsservice.domain.savingrule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingRule {

    private UUID id;
    private String name;
    private String description;
    private String detailedDescription;
    private String icon;
    private Category category;
    private String color;
    private Type type;
    private Status status;
    private SavingRuleConfigurations configurations;
    private UUID savingRuleAutomationId;
    private boolean hasAutomationEnabled;
    private boolean isRecommended;

    public enum Category {
        DEPOSIT, INVESTMENT
    }

    public enum Type {
        PROGRESSIVE,
        RECURRING,
        ROUNDING,
        SALARY,
        TARGET_VALUE
    }

    public enum Status {
        ACTIVE, INACTIVE, AVAILABLE_SOON
    }
}
