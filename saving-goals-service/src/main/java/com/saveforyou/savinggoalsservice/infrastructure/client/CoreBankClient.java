package com.saveforyou.savinggoalsservice.infrastructure.client;

import com.google.gson.Gson;
import com.saveforyou.savinggoalsservice.application.exceptions.UnProcessableEntityException;
import com.saveforyou.savinggoalsservice.infrastructure.client.model.CardChargeRequest;
import com.saveforyou.savinggoalsservice.infrastructure.client.model.TransferRequest;
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
public class CoreBankClient {

    @Value("${connection.timeout}")
    private Integer connectTimeout;

    @Value("${url.core-bank-service}")
    private String urlCoreBankService;

    @Value("${path.transfer-internal}")
    private String pathTransferInternal;

    @Value("${path.card-charge-internal}")
    private String pathCardChargeInternal;

    public void processTransferAmountInternal(TransferRequest transferRequest, UUID clientId) {
        try {
            var gson = new Gson();

            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(urlCoreBankService.concat(pathTransferInternal)))
                    .header("Content-Type", "application/json")
                    .header("clientId", String.valueOf(clientId))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(transferRequest)))
                    .timeout(Duration.ofSeconds(connectTimeout))
                    .build();

            var httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(connectTimeout))
                    .executor(Executors.newCachedThreadPool())
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

        } catch (Exception exception) {
            throw new UnProcessableEntityException(exception.getMessage());
        }
    }

    public void processCardChargeInternal(CardChargeRequest cardChargeRequest, UUID clientId) {
        try {
            var gson = new Gson();

            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(urlCoreBankService.concat(pathCardChargeInternal)))
                    .header("Content-Type", "application/json")
                    .header("clientId", String.valueOf(clientId))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(cardChargeRequest)))
                    .timeout(Duration.ofSeconds(connectTimeout))
                    .build();

            var httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(connectTimeout))
                    .executor(Executors.newCachedThreadPool())
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

        } catch (Exception exception) {
            throw new UnProcessableEntityException(exception.getMessage());
        }
    }
}
