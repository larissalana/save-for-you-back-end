package com.saveforyou.savinggoalsservice.application.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class NumberUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final Integer SCALE = 2;

    public static BigDecimal percentage(BigDecimal number1, BigDecimal number2){
        return number1.multiply(ONE_HUNDRED).divide(number2, SCALE, RoundingMode.HALF_UP);
    }
}