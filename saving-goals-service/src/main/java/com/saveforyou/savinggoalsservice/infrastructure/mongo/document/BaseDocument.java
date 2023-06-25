package com.saveforyou.savinggoalsservice.infrastructure.mongo.document;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class BaseDocument {

    @MongoId
    protected UUID id;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    public void setId(UUID id) {
        if(this.id != null) {
            throw new UnsupportedOperationException("ID is already defined");
        }
        this.id = id;
    }
}