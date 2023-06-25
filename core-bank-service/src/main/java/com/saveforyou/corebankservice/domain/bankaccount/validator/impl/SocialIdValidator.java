package com.saveforyou.corebankservice.domain.bankaccount.validator.impl;

import com.saveforyou.corebankservice.application.exceptions.BadRequestException;
import com.saveforyou.corebankservice.domain.bankaccount.validator.OpenBankAccountValidator;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.ClientRepository;
import io.swagger.model.OpenBankAccountRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialIdValidator implements OpenBankAccountValidator {

    private static final String SOCIAL_ID_INVALID = "É necessário informar um CPF ou CPNJ válido.";
    private static final String SOCIAL_ID_DUPLICATED = "Cliente já possui uma conta.";

    private final ClientRepository clientRepository;

    @Override
    public void validate(OpenBankAccountRequest openBankAccountRequest) {
        if (!isValidateCpf(openBankAccountRequest.getSocialId())
                && !isValidateCnpj(openBankAccountRequest.getSocialId())) {
            throw new BadRequestException(SOCIAL_ID_INVALID);
        } else if(isClientAlreadyRegistered(openBankAccountRequest.getSocialId())){
            throw new BadRequestException(SOCIAL_ID_DUPLICATED);
        }
    }

    private boolean isValidateCpf(String socialId){
        var cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        return cpfValidator.isValid(socialId, null);
    }

    private boolean isValidateCnpj(String socialId){
        var cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cnpjValidator.isValid(socialId, null);
    }

    private boolean isClientAlreadyRegistered(String socialId){
        return clientRepository.existsBySocialId(socialId);
    }
}