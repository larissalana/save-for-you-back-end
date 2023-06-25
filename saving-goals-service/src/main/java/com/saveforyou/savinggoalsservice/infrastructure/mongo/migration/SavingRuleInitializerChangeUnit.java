package com.saveforyou.savinggoalsservice.infrastructure.mongo.migration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleDocument;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.util.List;

import static com.saveforyou.savinggoalsservice.application.constants.AppConstants.APPLICATION_NAME;

@ChangeUnit(id="saving-rule-initializer", order = "2", author = APPLICATION_NAME, systemVersion = "1")
public class SavingRuleInitializerChangeUnit {

    private final MongoTemplate mongoTemplate;

    public SavingRuleInitializerChangeUnit(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SneakyThrows
    @Execution
    public void changeSet() {
        getDocuments().forEach(document -> mongoTemplate.save(document, AppConstants.SAVING_RULE_COLLECTION_NAME));
    }

    @RollbackExecution
    public void rollback() {}

    @SneakyThrows
    private List<SavingRuleDocument> getDocuments() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/db/migration_data/V2___saving_rules_data.json"), new TypeReference<List<SavingRuleDocument>>(){});
    }
}