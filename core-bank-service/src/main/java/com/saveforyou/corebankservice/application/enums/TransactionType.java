package com.saveforyou.corebankservice.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    DEPOSIT("Depósito", "#81D389", "arrow-downward"),
    SALARY_DEPOSIT("Depósito de Salário", "#F6D83E", "arrow-downward"),
    TRANSFER("Transaferência enviada", "#FFCA99", "repeat-one"),
    DEBIT_CHARGE("Cobrança no Débito", "#FFA0AC", "credit-card"),
    CREDIT_CHARGE("Cobrança no Crédito", "#FFA0AC", "credit-card"),
    WITHDRAWAL("Retirada", "#FFCA99", "arrow-upward"),
    INVESTMENT("Investimento", "#617486", ""),
    REDEEM("Resgate", "#617486", ""),
    AUTOMATION("Automatização", "#89D5FF", "settings");

    private final String description;
    private final String color;
    private final String icon;
}
