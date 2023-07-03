package com.example.board.api.board.app;

public class NotExistBoardException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "해당 게시글이 존재하지 않습니다.";

    public NotExistBoardException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
