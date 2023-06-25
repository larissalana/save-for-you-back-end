package com.saveforyou.savinggoalsservice.infrastructure.mongo.document;

import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = AppConstants.SAVING_RULE_AUTOMATION_COLLECTION_NAME)
public class SavingRuleAutomationDocument extends BaseDocument {

    @DocumentReference
    @Field("saving_goal")
    private SavingGoalDocument savingGoal;

    @DocumentReference
    @Field("saving_rule")
    private SavingRuleDocument savingRule;

    private Map<String, Object> configurations;

    private String status;
}