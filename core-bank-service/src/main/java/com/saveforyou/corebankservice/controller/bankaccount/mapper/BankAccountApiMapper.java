package com.saveforyou.corebankservice.controller.bankaccount.mapper;

import com.saveforyou.corebankservice.application.utils.MonetaryUtils;
import com.saveforyou.corebankservice.domain.bankaccount.model.BankAccount;
import io.swagger.model.BankAccountApiModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = {MonetaryUtils.class})
public interface BankAccountApiMapper {

    String SPACE = " ";

    @Mapping(expression = "java(MonetaryUtils.prettyValue(bankAccount.getBalance()))", target = "balancePretty")
    @Mapping(source = "client.name", target = "client.firstname", qualifiedByName = "getFirstname")
    BankAccountApiModel toApiModel(BankAccount bankAccount);

    @Named("getFirstname")
    default String getFirstname(String name){
        return name.split(SPACE)[0];
    }
}