package com.saveforyou.corebankservice.controller.transaction.mapper;

import com.saveforyou.corebankservice.application.enums.TransactionType;
import com.saveforyou.corebankservice.application.utils.DateUtils;
import com.saveforyou.corebankservice.application.utils.MonetaryUtils;
import com.saveforyou.corebankservice.infrastructure.persistence.entity.TransactionEntity;
import io.swagger.model.TransactionApiModel;
import io.swagger.model.TransactionsApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.saveforyou.corebankservice.application.enums.TransactionType.DEPOSIT;
import static com.saveforyou.corebankservice.application.enums.TransactionType.SALARY_DEPOSIT;

@Mapper(componentModel = "spring", imports = {TransactionType.class})
public interface TransactionApiMapper {

    @InheritConfiguration
    @Mapping(source = "number", target = "paginationInfo.currentPage")
    @Mapping(source = "totalPages", target = "paginationInfo.totalPages")
    @Mapping(source = "totalElements", target = "paginationInfo.totalItems")
    @Mapping(source = "content", target = "transactions")
    TransactionsApiModel toApiModel(Page<TransactionEntity> response);

    @Mapping(source = "amount", target = "amountPretty", qualifiedByName = "getPrettyAmount")
    @Mapping(source = "transaction", target = "description", qualifiedByName = "getDescription")
    @Mapping(source = "createdAt", target = "datePretty", qualifiedByName = "getPrettyDate")
    @Mapping(expression = "java(transaction.getType().getDescription())", target = "typeDescription")
    @Mapping(expression = "java(transaction.getType().getColor())", target = "typeColor")
    @Mapping(expression = "java(transaction.getType().getIcon())", target = "typeIcon")
    TransactionApiModel toApiModel(TransactionEntity transaction);

    @Named("getPrettyAmount")
    default String getPrettyAmount(BigDecimal value){
        return MonetaryUtils.prettyValue(value);
    }

    @Named("getPrettyDate")
    default String getPrettyDate(LocalDateTime date){
        return DateUtils.convertToPrettyDate(date);
    }

    @Named("getDescription")
    default String getDescription(TransactionEntity transaction){
        if(List.of(SALARY_DEPOSIT.name(), DEPOSIT.name()).contains(transaction.getType().name())){
            return transaction.getSenderDescription();
        }
        return transaction.getRecipientDescription();
    }
}