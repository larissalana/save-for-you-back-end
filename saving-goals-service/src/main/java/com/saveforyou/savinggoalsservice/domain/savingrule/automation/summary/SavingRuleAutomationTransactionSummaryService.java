package com.saveforyou.savinggoalsservice.domain.savingrule.automation.summary;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.TransactionsSummaryInformation;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.TransactionDocument;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingRuleAutomationTransactionSummaryService {

    private final TransactionRepository transactionRepository;

    public TransactionsSummaryInformation getSummaryInfo(UUID savingGoalId, UUID savingRuleId) {
        var transactions = transactionRepository.findAllBySavingGoalAndSavingRule(savingGoalId, savingRuleId);
        return TransactionsSummaryInformation.builder()
                .savingCountTotal(transactions.size())
                .savingAmountTotal(getSavingAmountTotal(transactions))
                .build();
    }

    private BigDecimal getSavingAmountTotal(List<TransactionDocument> transactions) {
        if (transactions.isEmpty()) return BigDecimal.ZERO;

        return transactions.stream()
                .map(TransactionDocument::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}