package com.saveforyou.savinggoalsservice.infrastructure.mongo.event;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.BaseDocument;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BaseDocumentEventListener extends AbstractMongoEventListener<BaseDocument> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseDocument> event) {

        super.onBeforeConvert(event);
        BaseDocument entity = event.getSource();

        if(entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
    }
}
