package com.saveforyou.savinggoalsservice.infrastructure.mongo.migration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.CategoryDocument;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.util.List;

import static com.saveforyou.savinggoalsservice.application.constants.AppConstants.APPLICATION_NAME;

@ChangeUnit(id="category-initializer", order = "1", author = APPLICATION_NAME, systemVersion = "1")
public class CategoryInitializerChangeUnit {

    private final MongoTemplate mongoTemplate;

    public CategoryInitializerChangeUnit(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SneakyThrows
    @Execution
    public void changeSet() {
        getDocuments().forEach(document -> mongoTemplate.save(document, AppConstants.CATEGORY_COLLECTION_NAME));
    }

    @RollbackExecution
    public void rollback() {}

    @SneakyThrows
    private List<CategoryDocument> getDocuments() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/db/migration_data/V1___categories_data.json"), new TypeReference<List<CategoryDocument>>(){});
    }
}