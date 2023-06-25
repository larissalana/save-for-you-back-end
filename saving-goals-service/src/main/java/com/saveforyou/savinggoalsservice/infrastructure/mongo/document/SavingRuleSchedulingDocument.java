package com.saveforyou.savinggoalsservice.infrastructure.mongo.document;

import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = AppConstants.SAVING_RULE_SCHEDULING_NAME)
public class SavingRuleSchedulingDocument extends BaseDocument {

    @DocumentReference
    @Field("saving_goal")
    private SavingGoalDocument savingGoal;

    @DocumentReference
    @Field("saving_rule")
    private SavingRuleDocument savingRule;

    @Field("execution_date")
    private LocalDate executionDate;

    private String status;
}
