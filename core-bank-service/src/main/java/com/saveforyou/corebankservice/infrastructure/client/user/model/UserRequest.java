package com.saveforyou.corebankservice.infrastructure.client.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String socialId;
    private String email;
    private String username;
    private String password;
    private UUID clientId;
}