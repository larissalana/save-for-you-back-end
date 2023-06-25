package com.saveforyou.savinggoalsservice.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    DEPOSIT("Depósito", "#81D389", "arrow-downward"),
    TRANSFER("Transaferência", "#C699FF", "flip-camera-android"),
    WITHDRAWAL("Retirada", "#C699FF", "arrow-upward"),
    INVESTMENT("Investimento", "#617486", "chart-bar"),
    REDEEM("Resgate", "#617486", ""),
    AUTOMATION("Automatização", "#89D5FF", "settings");

    private final String description;
    private final String color;
    private final String icon;
}
