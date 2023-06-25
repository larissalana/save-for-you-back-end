package com.saveforyou.corebankservice.domain.bankaccount.validator.impl;

import com.saveforyou.corebankservice.application.exceptions.BadRequestException;
import com.saveforyou.corebankservice.domain.bankaccount.validator.OpenBankAccountValidator;
import io.swagger.model.OpenBankAccountRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class DateOfBirthValidator implements OpenBankAccountValidator {

    private static final String DATE_OF_BIRTH_INVALID = "É necessário ter pelo menos 18 anos para abrir uma conta.";

    @Override
    public void validate(OpenBankAccountRequest openBankAccountRequest) {
        if (Period.between(openBankAccountRequest.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
            throw new BadRequestException(DATE_OF_BIRTH_INVALID);
        }
    }
}