package com.saveforyou.savinggoalsservice.infrastructure.mongo.repository;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.BaseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface CustomMongoRepository<T extends BaseDocument> extends MongoRepository<T, UUID> {

}
