package com.saveforyou.corebankservice.application.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DateUtils {

    public static String convertToPrettyDate(LocalDateTime localDateTime){
        var local = new Locale("pt","BR");
        var dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMM - HH:mm", local);
        return dateTimeFormatter.format(localDateTime).replace(".", "");
    }
}