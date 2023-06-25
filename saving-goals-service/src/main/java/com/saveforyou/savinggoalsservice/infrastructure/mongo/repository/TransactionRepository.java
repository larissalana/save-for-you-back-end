package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.TransactionDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionDocument, UUID> {

    Page<TransactionDocument> findBySavingGoalAndSavingRule(UUID savingGoalId, UUID savingRuleId, Pageable pageable);

    List<TransactionDocument> findAllBySavingGoalAndSavingRule(UUID savingGoalId, UUID savingRuleId);
}

