package com.saveforyou.savinggoalsservice.controller.savinggoal;

import com.saveforyou.savinggoalsservice.controller.savinggoal.mapper.SavingGoalApiMapper;
import com.saveforyou.savinggoalsservice.domain.savinggoal.SavingGoalService;
import io.swagger.api.SavingGoalApi;
import io.swagger.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SavingGoalController implements SavingGoalApi {

    private final SavingGoalApiMapper savingGoalApiMapper;
    private final SavingGoalService savingGoalService;

    @Override
    public ResponseEntity<SavingGoalApiModel> createSavingGoal(UUID clientId, CreateSavingGoalApiModel createSavingGoalApiModel){
        var savingGoalCreated = savingGoalApiMapper.toApiModel(savingGoalService.create(clientId, createSavingGoalApiModel));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savingGoalCreated.getId()).toUri();

        return ResponseEntity.created(uri).body(savingGoalCreated);
    }

    @Override
    public ResponseEntity<SavingGoalsSummaryApiModel> getSavingGoals(UUID clientId, Integer page, Integer size){
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(savingGoalApiMapper.toApiModel(savingGoalService.getAll(clientId, pageable)));
    }

    @Override
    public ResponseEntity<SavingGoalApiModel> getSavingGoalById(UUID id){
        return ResponseEntity.ok(savingGoalApiMapper.toApiModel(savingGoalService.getById(id)));
    }

    @Override
    public ResponseEntity<SavingGoalDetailsApiModel> getSavingGoalDetailsdById(UUID id) {
        return ResponseEntity.ok(savingGoalApiMapper.toApiModel(savingGoalService.getDetailsById(id)));
    }
}