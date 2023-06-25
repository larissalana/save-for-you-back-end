package com.saveforyou.corebankservice.domain.client.model;

import com.saveforyou.corebankservice.application.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private UUID id;
    private String name;
    private String socialId;
    private LocalDate dateOfBirth;
    private Status status;
    private LocalDateTime createdAt;
}