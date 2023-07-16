package com.example.board.api.auth.query;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.board.api.user.domain.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TokenGenerator {
    private static final long EXPIRED_AT_DEFAULT_HOUR = 3;
    private static final Algorithm ALGORITHM = Algorithm.none();

    public static String gen(User user) {
        return gen(user.getId(), user.getName());
    }

    public static String gen(Integer userId, String userName){
        return gen(userId, userName, getDefaultExpiredAt());
    }

    public static String gen(Integer userId, String userName, LocalDateTime expiredAt) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withExpiresAt(expiredAt.toInstant(ZoneOffset.UTC))
                .sign(ALGORITHM);
    }

    private static LocalDateTime getDefaultExpiredAt() {
        return LocalDateTime.now().plusHours(EXPIRED_AT_DEFAULT_HOUR);
    }
}
