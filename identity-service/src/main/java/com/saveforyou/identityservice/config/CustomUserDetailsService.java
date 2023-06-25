package com.saveforyou.identityservice.config;

import com.saveforyou.identityservice.config.model.CustomUserDetails;
import com.saveforyou.identityservice.domain.user.UserService;
import com.saveforyou.identityservice.domain.user.model.User;
import com.saveforyou.identityservice.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND = "Usuário não encontrado.";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return new CustomUserDetails(user.getUsername(), user.getPassword());
    }
}
