package com.saveforyou.identityservice.domain.auth;

import com.saveforyou.identityservice.domain.auth.jwt.JwtService;
import com.saveforyou.identityservice.domain.user.UserService;
import io.swagger.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public String generateToken(AuthRequest authRequest) {
        if (isAuthenticated(authRequest)) {
            var user = userService.getUserBy(authRequest.getUsername());
            return jwtService.generateToken(user);
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    private boolean isAuthenticated(AuthRequest authRequest){
        try {
            var authenticationRequest = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword());
            return authenticationManager.authenticate(authenticationRequest).isAuthenticated();
        } catch (Exception e) {
            return false;
        }
    }
}