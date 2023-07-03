package com.example.board.api.board.app;

import lombok.Builder;

public record BoardUpdateRequest(
        Integer seq,
        String title,
        String content,
        Integer userId
) {
    @Builder
    public BoardUpdateRequest {
    }
}
