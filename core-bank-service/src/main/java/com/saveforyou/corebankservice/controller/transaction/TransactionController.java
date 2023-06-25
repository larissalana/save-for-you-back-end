package com.saveforyou.corebankservice.controller.transaction;

import com.saveforyou.corebankservice.controller.transaction.mapper.TransactionApiMapper;
import com.saveforyou.corebankservice.domain.transaction.service.TransactionService;
import io.swagger.api.TransactionApi;
import io.swagger.model.TransactionsApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {

    private final TransactionApiMapper transactionApiMapper;
    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionsApiModel> getClientTransactions(UUID clientId, Integer page, Integer size) {
        var sort = Sort.by(Sort.Direction.DESC, "createdAt");
        var pageable = PageRequest.of(page, size, sort);
        var response = transactionService.getClientTransactions(clientId, pageable);
        return ResponseEntity.ok(transactionApiMapper.toApiModel(response));
    }
}
