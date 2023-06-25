package com.saveforyou.savinggoalsservice.domain.savinggoal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingRuleActive {

    private UUID savingRuleAutomationId;
    private UUID savingRuleId;
    private String savingRuleName;
    private String savingRuleColor;
    private String savingRuleIcon;
    private BigDecimal savingAmountTotal;
    private String savingAmountTotalPretty;
}
