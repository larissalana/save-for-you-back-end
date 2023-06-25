package com.saveforyou.savinggoalsservice.domain.savinggoal;

import com.saveforyou.savinggoalsservice.application.exceptions.NoSuchElementFoundException;
import com.saveforyou.savinggoalsservice.domain.savinggoal.mapper.SavingGoalMapper;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoal;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalDetails;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingGoalsSummary;
import com.saveforyou.savinggoalsservice.domain.savinggoal.model.SavingRuleActive;
import com.saveforyou.savinggoalsservice.domain.savinggoal.summary.SavingGoalSummaryService;
import com.saveforyou.savinggoalsservice.domain.savinggoal.validator.SavingGoalValidator;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.SavingRuleAutomationService;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.summary.SavingRuleAutomationTransactionSummaryService;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingGoalRepository;
import io.swagger.model.CreateSavingGoalApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingGoalService {

    private static final String SAVING_GOAL_NOT_FOUND = "Nenhum objetivo financeiro foi encontrado para os parâmetros informados.";

    private final List<SavingGoalValidator> validators;
    private final SavingGoalMapper savingGoalMapper;
    private final SavingGoalRepository savingGoalRepository;
    private final SavingGoalSummaryService savingGoalSummaryService;
    private final SavingRuleAutomationService savingRuleAutomationService;
    private final SavingRuleAutomationTransactionSummaryService savingRuleAutomationTransactionSummaryService;

    public SavingGoal create(UUID clientId, CreateSavingGoalApiModel createSavingGoalApiModel) {
        var savingGoal = savingGoalMapper.toCreate(clientId, createSavingGoalApiModel);
        var savingGoalValidated = doValidation(clientId, savingGoal);
        var savingGoalSaved = savingGoalRepository.save(savingGoalMapper.toDocument(savingGoalValidated));

        return savingGoalMapper.toDomainModel(savingGoalSaved);
    }

    public SavingGoalsSummary getAll(UUID clientId, PageRequest pageable) {
        var savingGoalDocuments = savingGoalRepository.findAllByClientId(clientId, pageable);

        if (savingGoalDocuments.isEmpty()) {
            throw new NoSuchElementFoundException(SAVING_GOAL_NOT_FOUND);
        }

        return savingGoalMapper.toDomainSummaryModel(savingGoalDocuments, savingGoalSummaryService.getSummaryInfo(clientId));
    }

    public SavingGoal getById(UUID id) {
        var savingGoalDocument = savingGoalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(SAVING_GOAL_NOT_FOUND));

        return savingGoalMapper.toDomainModel(savingGoalDocument);
    }

    public SavingGoalDetails getDetailsById(UUID id) {
        var savingGoal = getById(id);
        var savingRulesAutomationEnabled = savingRuleAutomationService.getAllEnabled(id);

        return enrichModel(savingGoal, savingRulesAutomationEnabled);
    }

    private SavingGoal doValidation(UUID clientId, SavingGoal savingGoal) {
        validators.forEach(validator -> validator.validate(clientId, savingGoal));
        return savingGoal;
    }

    private SavingGoalDetails enrichModel(SavingGoal savingGoal, List<SavingRuleAutomation> savingRulesAutomationEnabled) {
        var savingGoalDetails = new SavingGoalDetails();
        savingGoalDetails.setId(savingGoal.getId());
        savingGoalDetails.setSavingRulesActives(new ArrayList<>());

        savingGoalDetails.getSavingRulesActives()
                .addAll(savingRulesAutomationEnabled.stream().map(automation -> {
                    var savingRuleActive = new SavingRuleActive();
                    var summaryInfo = savingRuleAutomationTransactionSummaryService.getSummaryInfo(automation.getSavingGoal().getId(), automation.getSavingRule().getId());
                    savingRuleActive.setSavingRuleAutomationId(automation.getId());
                    savingRuleActive.setSavingRuleId(automation.getSavingRule().getId());
                    savingRuleActive.setSavingRuleName(automation.getSavingRule().getName());
                    savingRuleActive.setSavingRuleColor(automation.getSavingRule().getColor());
                    savingRuleActive.setSavingRuleIcon(automation.getSavingRule().getIcon());
                    savingRuleActive.setSavingAmountTotal(summaryInfo.getSavingAmountTotal());

                    return savingRuleActive;
                }).toList());

        return savingGoalDetails;
    }
}