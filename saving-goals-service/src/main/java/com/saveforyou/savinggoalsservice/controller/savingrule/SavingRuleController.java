package com.saveforyou.savinggoalsservice.controller.savingrule;

import com.saveforyou.savinggoalsservice.controller.savingrule.mapper.SavingRuleApiMapper;
import com.saveforyou.savinggoalsservice.domain.savingrule.SavingRuleService;
import io.swagger.api.SavingRuleApi;
import io.swagger.model.SavingRuleApiModel;
import io.swagger.model.SavingRuleCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SavingRuleController implements SavingRuleApi {

    private final SavingRuleApiMapper savingRuleApiMapper;
    private final SavingRuleService savingRuleService;

    @Override
    public ResponseEntity<List<SavingRuleApiModel>> getSavingRulesByCategory(UUID savingGoalId, SavingRuleCategory category) {
        return ResponseEntity.ok(savingRuleApiMapper.toApiModel(savingRuleService.getByCategory(savingGoalId, category)));
    }
}