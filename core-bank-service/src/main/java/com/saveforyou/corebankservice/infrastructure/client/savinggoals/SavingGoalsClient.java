package com.saveforyou.corebankservice.infrastructure.client.savinggoals;

import com.google.gson.Gson;
import com.saveforyou.corebankservice.application.exceptions.UnProcessableEntityException;
import com.saveforyou.corebankservice.infrastructure.client.savinggoals.model.TransactionNotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.Executors;

@Service
public class SavingGoalsClient {

    @Value("${connection.timeout}")
    private Integer connectTimeout;

    @Value("${url.saving-goals-service}")
    private String urlSavingGoalsService;

    @Value("${path.transaction-notification}")
    private String pathTransactionNotification;

    public void transactionNotification(TransactionNotificationRequest request, UUID clientId) {
        try{
            var gson = new Gson();

            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(urlSavingGoalsService.concat(pathTransactionNotification)))
                    .header("Content-Type", "application/json")
                    .header("clientId", clientId.toString())
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(request)))
                    .timeout(Duration.ofSeconds(connectTimeout))
                    .build();

            var httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(connectTimeout))
                    .executor(Executors.newCachedThreadPool())
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

        } catch (Exception exception){
            throw new UnProcessableEntityException(exception.getMessage());
        }
    }
}
