package com.saveforyou.apigateway.infrastructure.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class IdentityClient {

    @Value("${CONNECTION_TIMEOUT}")
    private Integer connectTimeout;

    @Value("${URL_IDENTITY_SERVICE}")
    private String urlIdentityService;

    @Value("${PATH_AUTH_VALIDATE}")
    private String pathAuthValidate;

    public void validateToken(String token) {
        try {
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(urlIdentityService.concat(pathAuthValidate)))
                    .header("Content-Type", "application/json")
                    .header("token", token)
                    .GET()
                    .timeout(Duration.ofSeconds(connectTimeout))
                    .build();

            var httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(connectTimeout))
                    .executor(Executors.newCachedThreadPool())
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
