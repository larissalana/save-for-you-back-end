package com.saveforyou.savinggoalsservice.controller.savinggoal.mapper;

import com.saveforyou.savinggoalsservice.application.utils.DateUtils;
import com.saveforyou.savinggoalsservice.application.utils.MonetaryUtils;
import com.saveforyou.savinggoalsservice.controller.category.mapper.CategoryApiMapper;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalDetails;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalsSummary;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingRuleActive;
import io.swagger.model.SavingGoalApiModel;
import io.swagger.model.SavingGoalDetailsApiModel;
import io.swagger.model.SavingGoalsSummaryApiModel;
import io.swagger.model.SavingRulesActivesApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = CategoryApiMapper.class)
public interface SavingGoalApiMapper {

    @Mapping(source = "targetAmount", target = "targetAmountPretty", qualifiedByName = "getPrettyAmount")
    @Mapping(source = "currentAmount", target = "currentAmountPretty", qualifiedByName = "getPrettyAmount")
    @Mapping(source = "targetDate", target = "targetDatePretty", qualifiedByName = "getPrettyDate")
    SavingGoalApiModel toApiModel(SavingGoal savingGoal);

    @InheritConfiguration
    @Mapping(source = "summaryInformation.savingAmountTotal", target = "summaryInformation.savingAmountTotalPretty", qualifiedByName = "getPrettyAmount")
    @Mapping(source = "summaryInformation.targetAmountTotal", target = "summaryInformation.targetAmountTotalPretty", qualifiedByName = "getPrettyAmount")
    SavingGoalsSummaryApiModel toApiModel(SavingGoalsSummary savingGoalsSummary);

    @InheritConfiguration
    SavingGoalDetailsApiModel toApiModel(SavingGoalDetails savingGoalDetails);

    @Mapping(source = "savingAmountTotal", target = "savingAmountTotalPretty", qualifiedByName = "getPrettyAmount")
    SavingRulesActivesApiModel toApiModel(SavingRuleActive savingRulesActives);

    @Named("getPrettyAmount")
    default String getPrettyAmount(BigDecimal value){
        return MonetaryUtils.prettyValue(value);
    }

    @Named("getPrettyDate")
    default String getPrettyDate(LocalDate date){
        return DateUtils.convertToPrettyDate(date);
    }
}