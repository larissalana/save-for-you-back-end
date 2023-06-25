package com.saveforyou.corebankservice.application.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class AccountNumberGenerator {

    private static final int ACCOUNT_NUMBER_LENGTH = 8;

    public static String generate(){
        var random = new Random();
        var accountNumberBuilder = new StringBuilder();

        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++){
            var digit = random.nextInt(10);
            accountNumberBuilder.append(digit);
        }

        return accountNumberBuilder.toString();
    }
}