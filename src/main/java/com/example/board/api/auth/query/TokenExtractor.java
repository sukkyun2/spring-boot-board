package com.example.board.api.auth.query;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.board.api.common.domain.BoardUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class TokenExtractor {
    private static final String TOKEN_KEY = "token";

    public static BoardUser extract(HttpServletRequest request) {
        try {
            return extractToken(request)
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


    private static Optional<String> extractToken(HttpServletRequest request){
        Optional<String> tokenInHeader = Optional.ofNullable(request.getHeader(TOKEN_KEY));
        Optional<String> tokenInCookie = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(TOKEN_KEY))
                        .map(Cookie::getValue)
                        .findAny());

        return tokenInHeader.isPresent() ? tokenInHeader : tokenInCookie;
    }
}
