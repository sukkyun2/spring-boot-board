package com.example.board.api.board.app;

import lombok.Builder;

public record BoardCreateRequest(
        String title,
        String content,
        Integer userId
) {
    @Builder
    public BoardCreateRequest {}
}
