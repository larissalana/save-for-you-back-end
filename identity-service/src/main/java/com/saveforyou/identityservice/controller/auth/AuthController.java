package com.saveforyou.identityservice.controller.auth;

import com.saveforyou.identityservice.controller.auth.mapper.AuthApiMapper;
import com.saveforyou.identityservice.domain.auth.AuthService;
import io.swagger.api.AuthApi;
import io.swagger.model.AuthRequest;
import io.swagger.model.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthApiMapper authApiMapper;
    private final AuthService authService;

    public ResponseEntity<AuthResponse> generateToken(AuthRequest authRequest){
        var response = authApiMapper.toApiModel(authService.generateToken(authRequest));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> validateToken(String token) {
        authService.validateToken(token);
        return ResponseEntity.noContent().build();
    }
}