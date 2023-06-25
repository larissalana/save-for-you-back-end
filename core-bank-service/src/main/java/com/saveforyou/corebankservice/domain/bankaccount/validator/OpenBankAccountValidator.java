package com.saveforyou.corebankservice.domain.bankaccount.validator;

import io.swagger.model.OpenBankAccountRequest;

public interface OpenBankAccountValidator {

    void validate(OpenBankAccountRequest openBankAccountRequest);
}
