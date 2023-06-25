package com.saveforyou.savinggoalsservice.domain.savingrule.scheduling.mapper;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.scheduling.model.SavingRuleScheduling;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleSchedulingDocument;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.saveforyou.savinggoalsservice.application.enums.DepositPropertiesKeys.START_DATE;

@Mapper(componentModel = "spring", imports = {SavingRuleScheduling.Status.class})
public interface SavingRuleSchedulingMapper {

    @Mapping(source = "savingGoal.id", target = "savingGoal.id")
    @Mapping(source = "savingRule.id", target = "savingRule.id")
    @Mapping(expression = "java(Status.PENDING.name())", target = "status")
    @Mapping(source = "configurations", target = "executionDate", qualifiedByName = "getExecutionDate")
    SavingRuleSchedulingDocument toCreate(SavingRuleAutomation savingRuleAutomation);

    SavingRuleScheduling toDomainModel(SavingRuleSchedulingDocument savingRuleSchedulingDocument);

    @InheritConfiguration
    List<SavingRuleScheduling> toDomainModel(List<SavingRuleSchedulingDocument> savingRuleSchedulingDocuments);

    @Named("getExecutionDate")
    default LocalDate getExecutionDate(Map<String, Object> configurations){
        return LocalDate.parse((configurations.get(START_DATE.getKey())).toString());
    }
}
