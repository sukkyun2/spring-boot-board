package com.example.board.api.auth.query;

import com.example.board.api.user.domain.UserRepository;
import com.example.board.api.user.query.NotExistUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest req) {
        return Optional.ofNullable(userRepository.findByLoginIdAndPassword(req.loginId(), req.password()))
                .map(TokenGenerator::gen)
                .map(LoginResponse::new)
                .orElseThrow(FailedLoginException::new);
    }
}
