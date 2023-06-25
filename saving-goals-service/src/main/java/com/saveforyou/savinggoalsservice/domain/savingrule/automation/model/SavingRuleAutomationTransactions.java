package com.saveforyou.savinggoalsservice.domain.savingrule.automation.model;

import com.saveforyou.savinggoalsservice.domain.commons.model.PaginationInfo;
import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SavingRuleAutomationTransactions {

    private List<Transaction> transactions;
    private TransactionsSummaryInformation summaryInformation;
    private PaginationInfo paginationInfo;
}