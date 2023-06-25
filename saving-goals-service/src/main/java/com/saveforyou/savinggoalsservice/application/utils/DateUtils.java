package com.saveforyou.savinggoalsservice.application.utils;

import com.saveforyou.savinggoalsservice.application.enums.UnitOfTime;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DateUtils {

    private static final String PATTERN = "EEE MMM d HH:mm:ss zzz yyyy";
    private static final String COMMA = ", ";
    private static final String COMMA_REGEX = "(.*), (.*)";
    private static final String REPLACEMENT = "$1 e $2";

    public static LocalDate convertStringToLocalDate(String date){
        var dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN, Locale.ENGLISH);
        return LocalDate.parse(date, dateTimeFormatter);
    }

    public static String convertToPrettyDate(LocalDate localDate){
        var local = new Locale("pt","BR");
        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM',' yyyy", local);
        return dateTimeFormatter.format(localDate).replace(".", "");
    }

    public static LocalDate convertToLocalDate(Object date){
        return LocalDate.parse(date.toString());
    }

    public static String findDifferenceBetween(LocalDate startDate, LocalDate endDate){
        var period = new StringBuilder();
        concatValid(UnitOfTime.YEARS, Period.between(startDate, endDate).getYears(), period);
        concatValid(UnitOfTime.MONTHS, Period.between(startDate, endDate).getMonths(), period);
        concatValid(UnitOfTime.DAYS, Period.between(startDate, endDate).getDays(), period);

        return period.substring(0, period.lastIndexOf(COMMA)).replaceAll(COMMA_REGEX, REPLACEMENT);
    }

    private static void concatValid(UnitOfTime unitOfTime, int unit, StringBuilder period){
        if (unit == 1) {
            period.append(String.format("%d %s, ", unit, unitOfTime.getSingular()));
        }
        else if (unit > 1) {
            period.append(String.format("%d %s, ", unit, unitOfTime.getPlural()));
        }
    }
}