package com.saveforyou.corebankservice.domain.client.mapper;

import com.saveforyou.corebankservice.application.enums.Status;
import com.saveforyou.corebankservice.domain.client.model.Client;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.ClientEntity;
import io.swagger.model.OpenBankAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Status.class})
public interface ClientMapper {

    @Mapping(expression = "java(Status.ACTIVE)", target = "status")
    Client toCreate(OpenBankAccountRequest openBankAccountRequest);

    Client toDomainModel(ClientEntity clientEntity);

    ClientEntity toEntity(Client client);
}
