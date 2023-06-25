package com.saveforyou.corebankservice.domain.user.mapper;

import com.saveforyou.corebankservice.infrastructure.client.user.model.UserRequest;
import io.swagger.model.OpenBankAccountRequest;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRequest toUserRequest(OpenBankAccountRequest openBankAccountRequest, UUID clientId);
}
