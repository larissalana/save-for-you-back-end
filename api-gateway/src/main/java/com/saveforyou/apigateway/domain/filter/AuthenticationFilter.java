package com.saveforyou.apigateway.domain.filter;

import com.saveforyou.apigateway.domain.identity.IdentityService;
import com.saveforyou.apigateway.domain.validator.RouteValidator;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

    @Autowired
    private RouteValidator validator;
    @Autowired
    private IdentityService identityService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                var token = getToken(exchange);

                if (token != null) {
                    identityService.validateToken(token);
                }
            }
            return chain.filter(exchange);
        });
    }

    private String getToken(ServerWebExchange exchange) {
        var headers = exchange.getRequest().getHeaders();

        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RuntimeException("Missing authorization header");
        }

        var authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);

        if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_BEARER)) {
            return authorizationHeader.replace(AUTHORIZATION_HEADER_BEARER, "");
        }
        return null;
    }
}
