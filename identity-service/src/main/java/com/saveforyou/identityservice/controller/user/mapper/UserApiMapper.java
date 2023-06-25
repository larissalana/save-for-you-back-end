package com.saveforyou.identityservice.controller.user.mapper;

import com.saveforyou.identityservice.domain.user.model.User;
import io.swagger.model.CreateUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserApiMapper {

    CreateUserResponse toApiModel(User user);
}