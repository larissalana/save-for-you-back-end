package com.saveforyou.savinggoalsservice.domain.savingrule.automation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsSummaryInformation {

    private Integer savingCountTotal;
    private BigDecimal savingAmountTotal;
}
