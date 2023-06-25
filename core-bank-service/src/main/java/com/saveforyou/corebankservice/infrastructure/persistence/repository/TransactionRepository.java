package com.saveforyou.corebankservice.infrastructure.persistence.repository;

import com.saveforyou.corebankservice.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Page<TransactionEntity> findByRecipientIdEqualsOrSenderIdEquals(UUID recipient, UUID sender, Pageable pageable);
}