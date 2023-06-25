package com.saveforyou.corebankservice.infrastructure.client.user;

import com.google.gson.Gson;
import com.saveforyou.corebankservice.application.exceptions.UnProcessableEntityException;
import com.saveforyou.corebankservice.infrastructure.client.user.model.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;

@Service
public class UserClient {

    @Value("${connection.timeout}")
    private Integer connectTimeout;

    @Value("${url.identity-service}")
    private String urlIdentityService;

    @Value("${path.user}")
    private String pathUser;

    public void createUser(UserRequest userRequest) {
        try{
            var gson = new Gson();

            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(urlIdentityService.concat(pathUser)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(userRequest)))
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
