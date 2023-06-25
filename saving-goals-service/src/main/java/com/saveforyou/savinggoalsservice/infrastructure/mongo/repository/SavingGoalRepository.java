package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingGoalDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SavingGoalRepository extends MongoRepository<SavingGoalDocument, UUID> {

    boolean existsByNameIgnoreCaseAndClientId(String name, UUID clientId);

    Page<SavingGoalDocument> findByStatusAndClientId(String status, UUID clientId, Pageable pageable);

    List<SavingGoalDocument> findByStatusAndClientId(String status, UUID clientId);

    Page<SavingGoalDocument> findAllByClientId(UUID clientId, Pageable pageable);


    @Aggregation(pipeline = {
            "{'$match': { 'client_id': ?0}}",
            "{'$group': { '_id': null, 'total' : { $sum: '$target_amount' } } }"
    })
    String sumTargetAmount(UUID clientId);

    @Aggregation(pipeline = {
            "{'$match': { 'client_id': ?0}}",
            "{'$group': { '_id': null, 'total' : { $sum: '$current_amount' } } }"
    })
    String sumCurrentAmount(UUID clientId);

    @Aggregation(pipeline = {
            "{'$match': { 'status': ?0, 'client_id': ?1}}",
            "{'$group': { '_id': null, 'total': { $sum: '$current_amount' } } }"
    })
    String sumCurrentAmountByStatus(String status, UUID clientId);

    @Aggregation(pipeline = {
            "{'$match': { 'client_id': ?0}}",
            "{'$group': { '_id': null, 'dateMax': { $max: '$target_date' } } }"
    })
    String maxTargetDate(UUID clientId);
}