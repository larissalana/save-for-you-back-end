package com.saveforyou.savinggoalsservice.domain.savingrule.automation.model;

import com.saveforyou.savinggoalsservice.application.enums.CardType;
import com.saveforyou.savinggoalsservice.application.enums.DepositFrequency;
import com.saveforyou.savinggoalsservice.application.enums.DepositValueType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DepositConfigurations {

    private DepositValueType depositValueType;
    private BigDecimal depositValue;
    private BigDecimal initialAmount;
    private BigDecimal incrementAmount;
    private BigDecimal limitAmount;
    private CardType cardType;
    private LocalDate startDate;
    private LocalDate endDate;
    private DepositFrequency depositFrequency;
}