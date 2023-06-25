package com.saveforyou.savinggoalsservice.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitOfTime {

    YEARS("anos", "ano"),
    MONTHS("meses", "mês"),
    DAYS("dias", "dia");

    private final String plural;
    private final String singular;
}
