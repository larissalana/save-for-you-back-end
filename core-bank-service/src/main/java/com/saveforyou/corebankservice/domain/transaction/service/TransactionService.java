package com.saveforyou.corebankservice.domain.transaction.service;

import com.saveforyou.corebankservice.domain.transaction.mapper.TransactionMapper;
import com.saveforyou.corebankservice.domain.transaction.model.Transaction;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.TransactionEntity;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public void create(Transaction transaction) {
        transactionRepository.save(transactionMapper.toEntity(transaction));
    }

    public Page<TransactionEntity> getClientTransactions(UUID clientId, PageRequest pageRequest) {
        return transactionRepository.findByRecipientIdEqualsOrSenderIdEquals(clientId, clientId, pageRequest);
    }
}
