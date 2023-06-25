package com.saveforyou.savinggoalsservice.domain.savinggoal.mapper;

import com.saveforyou.savinggoalsservice.domain.category.mapper.CategoryMapper;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalsSummary;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalsSummaryInformation;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingGoalDocument;
import io.swagger.model.CreateSavingGoalApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { BigDecimal.class, SavingGoal.Status.class} , uses = { CategoryMapper.class })
public interface SavingGoalMapper {

    @Mapping(expression = "java(BigDecimal.ZERO)", target = "currentAmount")
    @Mapping(expression = "java(Status.IN_PROGRESS)", target = "status")
    SavingGoal toCreate(UUID clientId, CreateSavingGoalApiModel createSavingGoalApiModel);

    @InheritConfiguration
    @Mapping(source = "page.number", target = "paginationInfo.currentPage")
    @Mapping(source = "page.totalElements", target = "paginationInfo.totalItems")
    @Mapping(source = "page.content", target = "savingGoals")
    SavingGoalsSummary toDomainSummaryModel(Page<SavingGoalDocument> page, SavingGoalsSummaryInformation summaryInformation);

    SavingGoal toDomainModel(SavingGoalDocument savingGoalDocument);

    @Mapping(expression = "java(savingGoal.getStatus().toString())", target = "status")
    SavingGoalDocument toDocument(SavingGoal savingGoal);
}