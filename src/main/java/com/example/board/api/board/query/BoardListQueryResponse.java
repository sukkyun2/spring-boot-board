package com.example.board.api.board.query;

import com.example.board.api.board.domain.Board;
import com.example.board.api.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record BoardListQueryResponse(
        Integer seq,
        String title,
        Integer regUserId,
        String regUserName,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        Integer viewCount
) {
    public static BoardListQueryResponse of(Board board, User user) {
        return new BoardListQueryResponse(
                board.getSeq(),
                board.getTitle(),
                board.getReg().getId(),
                user.getName(),
                board.getReg().getDt(),
                board.getViewCount());
    }
}
