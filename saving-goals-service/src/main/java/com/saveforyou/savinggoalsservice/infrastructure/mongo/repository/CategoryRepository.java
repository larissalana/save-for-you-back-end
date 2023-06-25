package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryDocument, UUID> {
}
