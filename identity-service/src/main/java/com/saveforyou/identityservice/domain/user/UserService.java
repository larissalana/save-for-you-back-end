package com.saveforyou.identityservice.domain.user;

import com.saveforyou.identityservice.domain.user.model.User;
import com.saveforyou.identityservice.domain.user.mapper.UserMapper;
import com.saveforyou.identityservice.infrastructure.persistence.repository.UserRepository;
import io.swagger.model.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "Usuário não encontrado.";

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(CreateUserRequest createUserRequest){
        var passwordEncoded = passwordEncoder.encode(createUserRequest.getPassword());
        var userToCreate = userMapper.toCreate(createUserRequest, passwordEncoded);
        var userSaved = userRepository.save(userMapper.toEntity(userToCreate));

        return userMapper.toDomainModel(userSaved);
    }

    public User getUserBy(String username) {
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        return userMapper.toDomainModel(userEntity);
    }
}
