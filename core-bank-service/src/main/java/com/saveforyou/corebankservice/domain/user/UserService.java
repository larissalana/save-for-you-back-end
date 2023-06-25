package com.saveforyou.corebankservice.domain.user;

import com.saveforyou.corebankservice.domain.user.mapper.UserMapper;
import com.saveforyou.corebankservice.infrastructure.client.user.UserClient;
import io.swagger.model.OpenBankAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserClient userClient;

    public void create(OpenBankAccountRequest openBankAccountRequest, UUID clientId) {
        var userRequest = userMapper.toUserRequest(openBankAccountRequest, clientId);
        userClient.createUser(userRequest);
    }
}
