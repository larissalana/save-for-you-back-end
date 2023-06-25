package com.saveforyou.corebankservice.infrastructure.persistence.repository;

import com.saveforyou.corebankservice.infrastructure.persistence.entity.FinancialEducationTipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialEducationTipRepository extends JpaRepository<FinancialEducationTipEntity, Long> {
}