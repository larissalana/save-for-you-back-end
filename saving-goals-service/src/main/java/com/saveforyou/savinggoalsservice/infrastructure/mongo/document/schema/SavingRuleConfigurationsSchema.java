package com.saveforyou.savinggoalsservice.infrastructure.mongo.document.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingRuleConfigurationsSchema {

    @Field("required_extra_configurations")
    private boolean requiredExtraConfigurations;

    @Field("required_deposit_value_type")
    private boolean requiredDepositValueType;

    @Field("required_deposit_value")
    private boolean requiredDepositValue;

    @Field("required_initial_amount")
    private boolean requiredInitialAmount;

    @Field("required_increment_amount")
    private boolean requiredIncrementAmount;

    @Field("required_limit_amount")
    private boolean requiredLimitAmount;

    @Field("required_card_type")
    private boolean requiredCardType;

    @Field("required_start_date")
    private boolean requiredStartDate;

    @Field("required_end_date")
    private boolean requiredEndDate;

    @Field("required_frequency")
    private boolean requiredFrequency;

    @Field("transaction_origin")
    private String transactionOrigin;
}