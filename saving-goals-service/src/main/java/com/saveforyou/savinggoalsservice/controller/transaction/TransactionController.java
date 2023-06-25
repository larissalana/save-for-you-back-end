package com.saveforyou.savinggoalsservice.controller.transaction;

import com.saveforyou.savinggoalsservice.domain.transaction.TransactionService;
import io.swagger.api.TransactionApi;
import io.swagger.model.TransactionNotificationApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<Void> processTransactionNotification(UUID clientId, TransactionNotificationApiModel transactionNotificationApiModel) {
        transactionService.processTransactionNotification(clientId, transactionNotificationApiModel);
        return ResponseEntity.ok().build();
    }
}
