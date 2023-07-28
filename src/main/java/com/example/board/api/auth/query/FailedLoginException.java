package com.example.board.api.auth.query;

public class FailedLoginException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "등록되지 않은 유저거나 아이디 혹은 패스워드가 다릅니다";

    public FailedLoginException() {
        super(DEFAULT_MESSAGE);
    }
}
