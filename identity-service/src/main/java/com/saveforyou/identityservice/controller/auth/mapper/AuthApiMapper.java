package com.saveforyou.identityservice.controller.auth.mapper;

import io.swagger.model.AuthResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthApiMapper {

    AuthResponse toApiModel(String token);
}
