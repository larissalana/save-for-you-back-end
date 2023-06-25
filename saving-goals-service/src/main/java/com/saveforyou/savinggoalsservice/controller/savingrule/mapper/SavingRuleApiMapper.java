package com.saveforyou.savinggoalsservice.controller.savingrule.mapper;

import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import io.swagger.model.SavingRuleApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SavingRuleApiMapper {

    @Mapping(source = "recommended", target = "isRecommended")
    SavingRuleApiModel toApiModel(SavingRule savingRule);

    @InheritConfiguration
    List<SavingRuleApiModel> toApiModel(List<SavingRule> savingRules);
}