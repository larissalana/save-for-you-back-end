package com.saveforyou.corebankservice.domain.savinggoal;

import com.saveforyou.corebankservice.infrastructure.client.savinggoals.SavingGoalsClient;
import com.saveforyou.corebankservice.infrastructure.client.savinggoals.model.TransactionNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavingGoalService {

    private final SavingGoalsClient savingGoalsClient;

    public void transactionNotification(UUID clientId, BigDecimal amount, String type) {
        log.info("--------- Send transaction Notification -------------");
        var transactionRequest = TransactionNotificationRequest.builder()
                .amount(amount)
                .type(type)
                .build();
        savingGoalsClient.transactionNotification(transactionRequest, clientId);
    }
}
