package com.saveforyou.savinggoalsservice.domain.savingrule.automation;

import com.saveforyou.savinggoalsservice.application.exceptions.NoSuchElementFoundException;
import com.saveforyou.savinggoalsservice.domain.savingrule.SavingRuleService;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.mapper.SavingRuleAutomationMapper;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.DepositConfigurations;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomationTransactions;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.summary.SavingRuleAutomationTransactionSummaryService;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.validator.SavingRuleAutomationValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.domain.savingrule.processor.factory.SavingRuleProcessorFactory;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingRuleAutomationRepository;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.TransactionRepository;
import io.swagger.model.SavingRuleAutomationApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingRuleAutomationService {

    private static final String SAVING_RULE_AUTOMATION_NOT_FOUND = "Nenhuma regra de automatização foi encontrada para os parâmetros informados.";

    private final SavingRuleAutomationMapper savingRuleAutomationMapper;
    private final SavingRuleAutomationRepository savingRuleAutomationRepository;
    private final SavingRuleService savingRuleService;
    private final SavingRuleProcessorFactory savingRuleProcessorFactory;
    private final SavingRuleAutomationTransactionSummaryService savingRuleAutomationTransactionSummaryService;
    private final TransactionRepository transactionRepository;

    private final List<SavingRuleAutomationValidator> validators;

    public SavingRuleAutomation getById(UUID id) {
        var savingRuleAutomationDocument = savingRuleAutomationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(SAVING_RULE_AUTOMATION_NOT_FOUND));

        return savingRuleAutomationMapper.toDomainModel(savingRuleAutomationDocument);
    }

    public List<SavingRuleAutomation> getAllEnabled(UUID savingGoalId) {
        var savingRuleAutomationDocuments = savingRuleAutomationRepository.findEnabledAutomationsBySavingGoalId(savingGoalId)
                .orElseThrow(() -> new NoSuchElementFoundException(SAVING_RULE_AUTOMATION_NOT_FOUND));

        return savingRuleAutomationMapper.toDomainModel(savingRuleAutomationDocuments);
    }

    public void configureAndEnable(UUID savingGoalId, UUID savingRuleId, SavingRuleAutomationApiModel apiModel) {
        var savingRule = savingRuleService.getById(savingRuleId);
        var configurations = doValidation(savingRule, savingRuleAutomationMapper.toConfigurations(apiModel));
        var savingRuleAutomation = savingRuleAutomationMapper.toEnable(savingGoalId, savingRuleId);
        var savingRuleProcessor = savingRuleProcessorFactory.getProcessor(savingRule);

        savingRuleProcessor.configureAndEnableAutomation(savingRuleAutomation, configurations);
    }

    public void disable(UUID id) {
        var savingRuleAutomation = getById(id);
        var savingRuleProcessor = savingRuleProcessorFactory.getProcessor(savingRuleAutomation.getSavingRule());

        savingRuleProcessor.disableAutomation(savingRuleAutomation);
    }

    private DepositConfigurations doValidation(SavingRule savingRule, DepositConfigurations configurations) {
        validators.stream().filter(validator -> validator.canValidate(savingRule))
                .forEach(validator -> validator.validate(configurations));
        return configurations;
    }

    public SavingRuleAutomationTransactions getSavingRuleAutomationTransactions(UUID savingGoalId, UUID savingRuleId, PageRequest pageable) {
        var transactionsDocument = transactionRepository.findBySavingGoalAndSavingRule(savingGoalId, savingRuleId, pageable);
        var summaryInfo = savingRuleAutomationTransactionSummaryService.getSummaryInfo(savingGoalId, savingRuleId);

        return savingRuleAutomationMapper.toDomainTransactionsModel(transactionsDocument, summaryInfo);
    }
}