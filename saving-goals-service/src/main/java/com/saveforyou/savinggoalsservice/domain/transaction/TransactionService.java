package com.saveforyou.savinggoalsservice.domain.transaction;

import com.saveforyou.savinggoalsservice.domain.savingrule.automation.mapper.SavingRuleAutomationMapper;
import com.saveforyou.savinggoalsservice.domain.transaction.mapper.TransactionMapper;
import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import com.saveforyou.savinggoalsservice.domain.transaction.processor.TransactionProcessor;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleAutomationDocument;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingGoalRepository;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingRuleAutomationRepository;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.TransactionRepository;
import io.swagger.model.TransactionNotificationApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static io.swagger.model.SavingGoalStatus.IN_PROGRESS;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final SavingGoalRepository savingGoalRepository;
    private final SavingRuleAutomationRepository savingRuleAutomationRepository;
    private final SavingRuleAutomationMapper savingRuleAutomationMapper;

    private final List<TransactionProcessor> transactionProcessors;

    public void create(Transaction transaction) {
        transactionRepository.save(transactionMapper.toDocument(transaction));
    }

    public void processTransactionNotification(UUID clientId, TransactionNotificationApiModel transactionNotificationApiModel) {
        var savingGoalsInProgressClient = savingGoalRepository.findByStatusAndClientId(IN_PROGRESS.toString(), clientId);

        savingGoalsInProgressClient.forEach(savingGoal -> {
            var savingRulesActives = savingRuleAutomationRepository.findEnabledAutomationsBySavingGoalId(savingGoal.getId());

            if (savingRulesActives.isPresent())
                processTransaction(savingRulesActives, transactionNotificationApiModel);
        });
    }

    private void processTransaction(Optional<List<SavingRuleAutomationDocument>> savingRulesActives, TransactionNotificationApiModel transactionNotificationApiModel) {
        savingRulesActives.get().forEach(automation -> {
            var savingGoalDocument = automation.getSavingGoal();
            var savingRuleAutomation = savingRuleAutomationMapper.toDomainModel(automation);

            transactionProcessors.stream()
                    .filter(p -> p.canProcess(savingRuleAutomation.getSavingRule(), transactionNotificationApiModel))
                    .forEach(p -> {
                        var transactionToSave = p.process(savingRuleAutomation, transactionNotificationApiModel);

                        if (Objects.nonNull(transactionToSave)) {
                            create(transactionToSave);
                            savingGoalRepository.save(savingGoalDocument.toUpdateCurrentAmount(transactionToSave.getAmount()));
                        }
                    });
        });
    }
}