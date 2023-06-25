package com.saveforyou.corebankservice.infrastructure.persistence.repository;

import com.saveforyou.corebankservice.infrastructure.persistence.entity.ClientEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    boolean existsBySocialId(@NotNull String socialId);

    ClientEntity findBySocialId(@NotNull String socialId);
}