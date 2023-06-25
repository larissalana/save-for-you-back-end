package com.saveforyou.savinggoalsservice.infrastructure.client.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class RecipientRequest {

    private String type;
    private UUID id;
    private String description;
}
