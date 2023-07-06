package com.example.board.api.board.query;

import com.example.board.api.board.domain.Board;
import com.example.board.api.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardListQueryResponse {
    private Integer seq;
    private String title;
    private Integer regUserId;
    private String regUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Integer viewCount;

    public static BoardListQueryResponse of(Board board, User user) {
        return new BoardListQueryResponse(
                board.getSeq(),
                board.getTitle(),
                board.getReg().getId(),
                user.name(),
                board.getReg().getDt(),
                board.getViewCount());
    }
}
