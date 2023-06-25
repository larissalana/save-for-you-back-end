package com.saveforyou.corebankservice.domain.transaction.mapper;

import com.saveforyou.corebankservice.domain.transaction.model.Transaction;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "recipient.id", target = "recipientId")
    @Mapping(source = "recipient.type", target = "recipientType")
    @Mapping(source = "recipient.description", target = "recipientDescription")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.type", target = "senderType")
    @Mapping(source = "sender.description", target = "senderDescription")
    TransactionEntity toEntity(Transaction transaction);
}
