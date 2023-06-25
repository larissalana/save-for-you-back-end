package com.saveforyou.apigateway.domain.identity;

import com.saveforyou.apigateway.infrastructure.client.IdentityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityService {

    private final IdentityClient identityClient;

    public void validateToken(String authHeader) {
        try {
            identityClient.validateToken(authHeader);
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized access to application");
        }
    }
}
