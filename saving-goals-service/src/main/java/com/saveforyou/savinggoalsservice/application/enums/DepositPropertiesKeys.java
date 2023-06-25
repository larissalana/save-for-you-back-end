package com.saveforyou.savinggoalsservice.application.enums;

public enum DepositPropertiesKeys {

    DEPOSIT_VALUE_TYPE("depositValueType"),
    DEPOSIT_VALUE("depositValue"),
    INITIAL_AMOUNT("initialAmount"),
    INCREMENT_AMOUNT("incrementAmount"),
    LIMIT_AMOUNT("limitAmount"),
    CARD_TYPE("cardType"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    DEPOSIT_FREQUENCY("depositFrequency");

    private final String key;

    DepositPropertiesKeys(String key){
        this.key = key;
    }

    public String getKey(){ return key;}
}
