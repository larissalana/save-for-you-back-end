package com.saveforyou.savinggoalsservice.infrastructure.mongo.document;

import com.saveforyou.savinggoalsservice.application.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = AppConstants.SAVING_GOAL_COLLECTION_NAME)
public class SavingGoalDocument extends BaseDocument {

    @Indexed
    @Field("client_id")
    private UUID clientId;

    @Indexed(unique = true)
    private String name;

    private String icon;

    @DocumentReference
    private CategoryDocument category;

    @Field(name = "target_amount", targetType = DECIMAL128)
    private BigDecimal targetAmount;

    @Field("target_date")
    private LocalDate targetDate;

    @Field(name = "current_amount", targetType = DECIMAL128)
    private BigDecimal currentAmount;

    @Field("achieved_date")
    private LocalDate achievedDate;

    private String status;

    public SavingGoalDocument toUpdateCurrentAmount(BigDecimal amount){
        this.currentAmount = this.currentAmount.add(amount);
        if(this.currentAmount.compareTo(this.targetAmount) >= 0){
            this.status = "ACHIEVED";
            this.achievedDate = LocalDate.now();
        }
        return this;
    }
}