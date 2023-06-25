package com.saveforyou.corebankservice.domain.transaction.model;

import com.saveforyou.corebankservice.application.enums.SenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Sender {

    private SenderType type;
    private UUID id;
    private String description;
}