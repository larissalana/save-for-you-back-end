package com.saveforyou.corebankservice.application.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class MonetaryUtils {

    public static String prettyValue(BigDecimal value){
        var numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return numberFormat.format(value);
    }
}
