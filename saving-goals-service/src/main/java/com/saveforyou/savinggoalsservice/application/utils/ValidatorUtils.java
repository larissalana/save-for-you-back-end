package com.saveforyou.savinggoalsservice.application.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Objects;

@UtilityClass
public class ValidatorUtils {

    private static final double MIN_AMOUNT = 0.10;
    private static final double MIN_PERCENTAGE = 1;
    private static final double MAX_PERCENTAGE = 20;

    public static boolean isAmountInvalid(BigDecimal amount){
        return Objects.nonNull(amount) && amount.compareTo(new BigDecimal(MIN_AMOUNT)) <= 0;
    }

    public static boolean isPercentageInvalid(BigDecimal percentage){
        return percentage.compareTo(new BigDecimal(MIN_PERCENTAGE)) <= 0
                && percentage.compareTo(new BigDecimal(MAX_PERCENTAGE)) >= 0;
    }
}