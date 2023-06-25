package com.saveforyou.identityservice.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String name;
    private String socialId;
    private String email;
    private String username;
    private String password;
    private Status status;
    private UUID clientId;

    public enum Status {
        ACTIVE,
        INACTIVE,
        BLOCKED
    }
}
