package com.saveforyou.savinggoalsservice.controller.savingrule.automation.mapper;

import com.saveforyou.savinggoalsservice.application.enums.TransactionType;
import com.saveforyou.savinggoalsservice.application.utils.DateUtils;
import com.saveforyou.savinggoalsservice.application.utils.MonetaryUtils;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomationTransactions;
import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import io.swagger.model.SavingRuleAutomationApiModel;
import io.swagger.model.SavingRuleAutomationReponseApiModel;
import io.swagger.model.SavingRuleAutomationTransactionApiModel;
import io.swagger.model.SavingRuleAutomationTransactionsApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {TransactionType.class})
public interface SavingRuleAutomationApiMapper {

    SavingRuleAutomationApiModel toApiModel(SavingRuleAutomation savingRuleAutomation);

    SavingRuleAutomationReponseApiModel toResponseApiModel(SavingRuleAutomation savingRuleAutomation);

    @InheritConfiguration
    @Mapping(source = "summaryInformation.savingAmountTotal", target = "summaryInformation.savingAmountTotalPretty", qualifiedByName = "getPrettyAmount")
    SavingRuleAutomationTransactionsApiModel toTransactionsApiModel(SavingRuleAutomationTransactions savingRuleAutomationTransactions);

    @Mapping(source = "savingRule.id", target = "savingRuleId")
    @Mapping(source = "savingRule.name", target = "savingRuleName")
    @Mapping(source = "amount", target = "amountPretty", qualifiedByName = "getPrettyAmount")
    @Mapping(source = "date", target = "datePretty", qualifiedByName = "getPrettyDate")
    @Mapping(expression = "java(transaction.getType().getDescription())", target = "typeDescription")
    @Mapping(expression = "java(transaction.getType().getColor())", target = "typeColor")
    @Mapping(expression = "java(transaction.getType().getIcon())", target = "typeIcon")
    SavingRuleAutomationTransactionApiModel toTransactionApiModel(Transaction transaction);

    @Named("getPrettyAmount")
    default String getPrettyAmount(BigDecimal value){
        return MonetaryUtils.prettyValue(value);
    }

    @Named("getPrettyDate")
    default String getPrettyDate(LocalDateTime date){
        return DateUtils.convertToPrettyDate(date.toLocalDate());
    }
}