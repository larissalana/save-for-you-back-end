package com.saveforyou.corebankservice.domain.bankaccount.mapper;

import com.saveforyou.corebankservice.application.enums.Status;
import com.saveforyou.corebankservice.domain.bankaccount.model.BankAccount;
import com.saveforyou.corebankservice.domain.client.model.Client;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.BankAccountEntity;
import io.swagger.model.OpenBankAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = {Status.class, BankAccount.BankAccountType.class, BigDecimal.class})
public interface BankAccountMapper {

    String BRANCH_DEFAULT = "001";

    @Mapping(constant = BRANCH_DEFAULT, target = "branch")
    @Mapping(expression = "java(BankAccountType.CHECKING_ACCOUNT)", target = "type")
    @Mapping(expression = "java(Status.ACTIVE)", target = "status")
    @Mapping(expression = "java(BigDecimal.ZERO)", target = "balance")
    @Mapping(source = "client", target = "client")
    BankAccount toCreate(String accountNumber, Client client);

    BankAccount toDomainModel(BankAccountEntity bankAccountEntity);

    BankAccountEntity toEntity(BankAccount bankAccount);

    OpenBankAccountResponse toResponse(BankAccount bankAccount);
}