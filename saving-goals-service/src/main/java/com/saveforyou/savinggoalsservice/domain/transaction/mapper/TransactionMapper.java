package com.saveforyou.savinggoalsservice.domain.transaction.mapper;

import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.TransactionDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDocument toDocument(Transaction transaction);
}