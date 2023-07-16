package com.example.board.api.auth.query;

public record LoginRequest(
        String loginId,
        String password
) {
}
