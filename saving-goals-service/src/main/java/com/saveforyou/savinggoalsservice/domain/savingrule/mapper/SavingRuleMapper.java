package com.saveforyou.savinggoalsservice.domain.savingrule.mapper;

import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleDocument;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", imports = { SavingRule.Category.class, SavingRule.Status.class})
public interface SavingRuleMapper {

    SavingRule toDomainModel(SavingRuleDocument savingRuleDocument);

    @InheritConfiguration
    List<SavingRule> toDomainModel(List<SavingRuleDocument> savingRuleDocuments);
}