package com.example.board.api.auth.query;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.board.api.common.domain.BoardUser;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Optional;

public class TokenExtractor {
    public static BoardUser extract(HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getHeader("token"))
                    .map(JWT::decode)
                    .map(decodedToken -> BoardUser.builder()
                            .userId(decodedToken.getClaim("userId").asInt())
                            .userName(decodedToken.getClaim("userName").asString())
                            .expired(decodedToken.getExpiresAt().before(new Date()))
                            .userIp(request.getRemoteAddr())
                            .build())
                    .orElse(null);
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
