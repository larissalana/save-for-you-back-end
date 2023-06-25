package com.saveforyou.savinggoalsservice.domain.savingrule;

import com.saveforyou.savinggoalsservice.application.exceptions.NoSuchElementFoundException;
import com.saveforyou.savinggoalsservice.domain.savingrule.mapper.SavingRuleMapper;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.SavingRuleAutomationDocument;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingRuleAutomationRepository;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.SavingRuleRepository;
import io.swagger.model.SavingRuleCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Status.ACTIVE;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Type.ROUNDING;

@Service
@RequiredArgsConstructor
public class SavingRuleService {

    private static final String SAVING_RULE_NOT_FOUND = "Nenhuma regra de automatização foi encontrada para os parâmetros informados.";

    private final SavingRuleMapper savingRuleMapper;
    private final SavingRuleRepository savingRuleRepository;
    private final SavingRuleAutomationRepository savingRuleAutomationRepository;

    public SavingRule getById(UUID id) {
        var savingRuleDocument = savingRuleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(SAVING_RULE_NOT_FOUND));

        return savingRuleMapper.toDomainModel(savingRuleDocument);
    }

    public List<SavingRule> getByCategory(UUID savingGoalId, SavingRuleCategory category) {
        var savingRuleDocuments = savingRuleRepository.findByCategoryAndStatus(category.toString(), ACTIVE.toString())
                .orElseThrow(() -> new NoSuchElementFoundException(SAVING_RULE_NOT_FOUND));

        var savingRules = savingRuleMapper.toDomainModel(savingRuleDocuments);

        return enrichModel(savingRules, savingGoalId);
    }

    private List<SavingRule> enrichModel(List<SavingRule> savingRules, UUID savingGoalId) {
        var savingRulesActives = savingRuleAutomationRepository.findEnabledAutomationsBySavingGoalId(savingGoalId);

        savingRules.forEach(savingRule -> {
            getCorrespondingActiveRule(savingRulesActives, savingRule).ifPresent(
                    ruleActive -> {
                        savingRule.setSavingRuleAutomationId(ruleActive.getId());
                        savingRule.setHasAutomationEnabled(true);
                    }
            );
            savingRule.setRecommended(isSavingRuleRecommended(savingRule));
        });

        return savingRules.stream()
                .filter(savingRule -> Boolean.TRUE.equals(savingRule.isHasAutomationEnabled()))
                .collect(Collectors.toList());
    }

    private Optional<SavingRuleAutomationDocument> getCorrespondingActiveRule(Optional<List<SavingRuleAutomationDocument>> savingRulesActives, SavingRule savingRule) {
        return savingRulesActives.flatMap(savingRuleAutomationDocuments -> savingRuleAutomationDocuments.stream()
                .filter(ruleActive -> ruleActive.getSavingRule().getId().equals(savingRule.getId()))
                .findFirst());
    }

    private boolean isSavingRuleRecommended(SavingRule savingRule) {
        return ROUNDING.equals(savingRule.getType());
    }
}