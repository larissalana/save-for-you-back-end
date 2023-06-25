package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleAutomationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SavingRuleAutomationRepository extends MongoRepository<SavingRuleAutomationDocument, UUID> {

    Optional<SavingRuleAutomationDocument> findBySavingGoalAndSavingRule(UUID savingGoalId, UUID savingRuleId);

    @Query(value="{$and: [{ 'status': 'ENABLED' }, { 'saving_goal': ?0 } ]}")
    Optional<List<SavingRuleAutomationDocument>> findEnabledAutomationsBySavingGoalId(UUID savingGoalId);
}