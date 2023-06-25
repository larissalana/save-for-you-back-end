package com.saveforyou.savinggoalsservice.controller.savingrule.automation;

import com.saveforyou.savinggoalsservice.controller.savingrule.automation.mapper.SavingRuleAutomationApiMapper;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.SavingRuleAutomationService;
import io.swagger.api.SavingRuleAutomationApi;
import io.swagger.model.SavingRuleAutomationApiModel;
import io.swagger.model.SavingRuleAutomationReponseApiModel;
import io.swagger.model.SavingRuleAutomationTransactionsApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SavingRuleAutomationController implements SavingRuleAutomationApi {

    private final SavingRuleAutomationApiMapper savingRuleAutomationApiMapper;
    private final SavingRuleAutomationService savingRuleAutomationService;

    @Override
    public ResponseEntity<SavingRuleAutomationReponseApiModel> getSavingRuleAutomationById(UUID id) {
        var response = savingRuleAutomationService.getById(id);
        return ResponseEntity.ok(savingRuleAutomationApiMapper.toResponseApiModel(response));
    }

    @Override
    public ResponseEntity<Void> configureAndEnableSavingRuleAutomation(UUID savingGoalId, UUID savingRuleId, SavingRuleAutomationApiModel savingRuleAutomationApiModel) {
        savingRuleAutomationService.configureAndEnable(savingGoalId, savingRuleId, savingRuleAutomationApiModel);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> disableSavingRuleAutomation(UUID id){
        savingRuleAutomationService.disable(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SavingRuleAutomationTransactionsApiModel> getSavingRuleAutomationTransactions(UUID savingGoalId, UUID savingRuleId, Integer page, Integer size){
        var sort = Sort.by(Sort.Direction.DESC, "date");
        var pageable = PageRequest.of(page, size, sort);
        var response = savingRuleAutomationService.getSavingRuleAutomationTransactions(savingGoalId, savingRuleId, pageable);
        return ResponseEntity.ok(savingRuleAutomationApiMapper.toTransactionsApiModel(response));
    }
}