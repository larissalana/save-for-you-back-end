package com.saveforyou.corebankservice.domain.card.mapper;

import com.saveforyou.corebankservice.application.enums.Status;
import com.saveforyou.corebankservice.application.utils.CardGenerator;
import com.saveforyou.corebankservice.domain.card.model.Card;
import com.saveforyou.corebankservice.domain.client.model.Client;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = {Status.class, BigDecimal.class, CardGenerator.class})
public interface CardMapper {

    @Mapping(source = "name", target = "holderName", qualifiedByName = "getHolderName")
    @Mapping(expression = "java(CardGenerator.generateNumber())", target = "number")
    @Mapping(expression = "java(CardGenerator.generateExpirationDate())", target = "expirationDate")
    @Mapping(expression = "java(CardGenerator.generateCVV())", target = "securityCode")
    @Mapping(expression = "java(BigDecimal.valueOf(5000.00))", target = "creditLimit")
    @Mapping(expression = "java(BigDecimal.ZERO)", target = "balance")
    @Mapping(expression = "java(Status.ACTIVE)", target = "status")
    @Mapping(source = "client", target = "client")
    @Mapping(target = "id", ignore = true)
    Card toCreate(Client client);

    Card toDomainModel(CardEntity cardEntity);

    CardEntity toEntity(Card card);

    @Named("getHolderName")
    default String getHolderName(String name){
        var nameSplit = name.split(" ");
        return nameSplit[0] + " " + nameSplit[(nameSplit.length-1)];
    }
}