package com.saveforyou.corebankservice.infrastructure.persistence.repository;

import com.saveforyou.corebankservice.infrastructure.persistence.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {

    Optional<BankAccountEntity> findByClientId(UUID clientId);

    Optional<BankAccountEntity> findByBranchAndAccountNumber(String branch, String accountNumber);
}