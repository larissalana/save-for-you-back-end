package com.saveforyou.savinggoalsservice.infrastructure.mongo.document;

import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.schema.SavingRuleConfigurationsSchema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = AppConstants.SAVING_RULE_COLLECTION_NAME)
public class SavingRuleDocument extends BaseDocument {

    private String name;

    private String description;

    @Field("detailed_description")
    private String detailedDescription;

    private String icon;

    private String color;

    private String category;

    private String type;

    private String status;

    private SavingRuleConfigurationsSchema configurations;
}
