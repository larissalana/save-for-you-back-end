package com.saveforyou.apigateway.domain.validator;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/user",
            "/auth/token",
            "/auth/validate",
            "/eureka"
    );// TODO ajustar por causa do token para o endpoint de criação da conta

    public Predicate<ServerHttpRequest> isSecured = request ->
            openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}