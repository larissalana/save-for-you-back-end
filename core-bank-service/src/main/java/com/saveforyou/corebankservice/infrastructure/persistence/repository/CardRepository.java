package com.saveforyou.corebankservice.infrastructure.persistence.repository;

import com.saveforyou.corebankservice.infrastructure.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    Optional<CardEntity> findByClient_Id(UUID clientId);
}