package com.saveforyou.savinggoalsservice.domain.transaction.model;

import com.saveforyou.savinggoalsservice.application.enums.TransactionStatus;
import com.saveforyou.savinggoalsservice.application.enums.TransactionType;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private UUID id;
    private SavingGoal savingGoal;
    private SavingRule savingRule;
    private BigDecimal amount;
    private LocalDateTime date;
    private TransactionType type;
    private TransactionStatus status;

    public static Transaction from(SavingRuleAutomation savingRuleAutomation, BigDecimal amount){
        return Transaction.builder()
                .savingGoal(savingRuleAutomation.getSavingGoal())
                .savingRule(savingRuleAutomation.getSavingRule())
                .amount(amount)
                .date(LocalDateTime.now())
                .type(TransactionType.AUTOMATION)
                .status(TransactionStatus.PROCESSED)
                .build();
    }
}
