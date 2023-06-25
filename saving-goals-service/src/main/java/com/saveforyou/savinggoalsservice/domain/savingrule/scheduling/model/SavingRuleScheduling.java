package com.saveforyou.savinggoalsservice.domain.savingrule.scheduling.model;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingRuleScheduling {

    private UUID id;
    private SavingRuleAutomation savingRuleAutomation;
    private LocalDate executionDate;
    private Status status;

    public enum Status {
        PENDING,
        EXECUTED,
        PROCESSED,
        CANCELED
    }
}
