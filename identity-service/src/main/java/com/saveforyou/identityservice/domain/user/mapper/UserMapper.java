package com.saveforyou.identityservice.domain.user.mapper;

import com.saveforyou.identityservice.domain.user.model.User;
import com.saveforyou.identityservice.infrastructure.persistence.entity.UserEntity;
import io.swagger.model.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { User.Status.class})
public interface UserMapper {

    @Mapping(expression = "java(User.Status.ACTIVE)", target = "status")
    @Mapping(source = "passwordEncoded", target = "password")
    User toCreate(CreateUserRequest request, String passwordEncoded);

    UserEntity toEntity(User user);

    User toDomainModel(UserEntity userEntity);
}
