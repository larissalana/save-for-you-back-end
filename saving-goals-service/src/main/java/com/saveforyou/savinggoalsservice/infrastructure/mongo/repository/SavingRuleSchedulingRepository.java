package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleSchedulingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SavingRuleSchedulingRepository extends MongoRepository<SavingRuleSchedulingDocument, UUID> {

    @Query(value="{$and: [{ 'status': 'PENDING' }, { 'saving_goal': ?0 }, { 'saving_rule': ?1 } ]}", delete = true)
    void deleteAllPendingBy(UUID savingGoalId, UUID savingRuleId);
}
