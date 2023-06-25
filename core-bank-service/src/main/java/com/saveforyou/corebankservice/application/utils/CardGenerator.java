package com.saveforyou.corebankservice.application.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class CardGenerator {

    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int GROUP_SIZE = 4;
    private static final int BIN = 2523;
    private static final int EXPIRATION_YEAR_MIN = 2023;
    private static final int EXPIRATION_YEAR_MAX = 2028;
    private static final int CVV_MIN = 100;
    private static final int CVV_MAX = 999;

    public static String generateNumber(){
        var random = new Random();
        var cardNumberBuilder = new StringBuilder();

        cardNumberBuilder.append(BIN);

        for (int i = 4; i < CARD_NUMBER_LENGTH; i++){
            var digit = random.nextInt(10);

            if(i % GROUP_SIZE == 0){
                cardNumberBuilder.append("-");
            }
            cardNumberBuilder.append(digit);
        }

        return cardNumberBuilder.toString();
    }

    public static String generateExpirationDate(){
        var random = new Random();
        var month = normalizeMonth(random.nextInt(12) + 1);
        var year = random.nextInt(EXPIRATION_YEAR_MAX - EXPIRATION_YEAR_MIN + 1) + EXPIRATION_YEAR_MIN;

        return month + "/" + year;
    }

    public static String generateCVV(){
        var random = new Random();
        var cvv = random.nextInt(CVV_MAX - CVV_MIN + 1) + CVV_MIN;

        return String.valueOf(cvv);
    }

    private static String normalizeMonth(int month) {
        return month < 10 ? "0" + month : String.valueOf(month);
    }
}
