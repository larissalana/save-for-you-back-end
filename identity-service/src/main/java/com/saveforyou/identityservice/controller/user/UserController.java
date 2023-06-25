package com.saveforyou.identityservice.controller.user;

import com.saveforyou.identityservice.controller.user.mapper.UserApiMapper;
import com.saveforyou.identityservice.domain.user.UserService;
import io.swagger.api.UserApi;
import io.swagger.model.CreateUserRequest;
import io.swagger.model.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserApiMapper userApiMapper;
    private final UserService userService;

    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest){
        var response = userApiMapper.toApiModel(userService.create(createUserRequest));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}