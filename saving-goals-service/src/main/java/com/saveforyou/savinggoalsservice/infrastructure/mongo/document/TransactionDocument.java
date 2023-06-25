package com.saveforyou.savinggoalsservice.infrastructure.mongo.document;

import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = AppConstants.TRANSACTION_NAME)
public class TransactionDocument extends BaseDocument {

    @DocumentReference
    private SavingGoalDocument savingGoal;

    @DocumentReference
    private SavingRuleDocument savingRule;

    @Field(name = "amount", targetType = DECIMAL128)
    private BigDecimal amount;

    private LocalDateTime date;

    private String type;

    private String status;
}