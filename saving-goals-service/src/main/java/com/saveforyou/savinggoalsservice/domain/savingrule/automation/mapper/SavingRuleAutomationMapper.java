package com.saveforyou.savinggoalsservice.domain.savingrule.automation.mapper;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomationTransactions;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.TransactionsSummaryInformation;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleAutomationDocument;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.TransactionDocument;
import io.swagger.model.SavingRuleAutomationApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {SavingRuleAutomation.Status.class})
public interface SavingRuleAutomationMapper {

    @Mapping(source = "savingGoalId", target = "savingGoal.id")
    @Mapping(source = "savingRuleId", target = "savingRule.id")
    @Mapping(expression = "java(Status.ENABLED)", target = "status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "configurations", ignore = true)
    SavingRuleAutomation toEnable(UUID savingGoalId, UUID savingRuleId);

    SavingRuleAutomationDocument toDocument(SavingRuleAutomation savingRuleAutomation);

    SavingRuleAutomation toDomainModel(SavingRuleAutomationDocument savingRuleAutomationDocument);

    @InheritConfiguration
    List<SavingRuleAutomation> toDomainModel(List<SavingRuleAutomationDocument> savingRuleAutomationDocuments);

    DepositConfigurations toConfigurations(SavingRuleAutomationApiModel apiModel);

    @InheritConfiguration
    @Mapping(source = "page.number", target = "paginationInfo.currentPage")
    @Mapping(source = "page.totalElements", target = "paginationInfo.totalItems")
    @Mapping(source = "page.content", target = "transactions")
    SavingRuleAutomationTransactions toDomainTransactionsModel(Page<TransactionDocument> page, TransactionsSummaryInformation summaryInformation);
}