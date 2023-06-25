package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleDocument;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SavingRuleRepository extends MongoRepository<SavingRuleDocument, UUID> {

   Optional<List<SavingRuleDocument>> findByCategoryAndStatus(String category, String status);

   @Aggregation(pipeline = {
           "{'$match': { 'category': ?1 }}",
           "{'$lookup': { " +
                   "'from': 'saving_rules_automations', " +
                   "'let' : { saving_rule: '$_id' }, " +
                   "'as' : 'saving_rule', " +
                   "'pipeline': [ " +
                      "{ '$match': { " +
                   "       '$and': [ " +
                   "          { '$expr': { '$eq': ['$saving_rule', '$$saving_rule'] } }, " +
                   "          { 'saving_goal': ?0 }, " +
                   "          { 'status': 'ENABLED' } " +
                   "        ] } } ] " +
                   "} }",
           "{'$match': { 'saving_rule': { '$ne': [] } }}",
           "{'$project': { 'saving_rule': 0 }}"
   })
   List<SavingRuleDocument> findAllByCategoryWithAutomationEnabled(UUID savingGoalId, String category);
}