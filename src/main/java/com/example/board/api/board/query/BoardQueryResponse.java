package com.example.board.api.board.query;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.query.comment.CommentQueryResponse;
import com.example.board.api.common.domain.Upd;
import com.example.board.api.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record BoardQueryResponse(
        Integer seq,
        String title,
        String content,
        List<CommentQueryResponse> comments,
        Integer regId,
        String regUserName,
        LocalDateTime regDate,
        Integer updId,
        String updUserName,
        LocalDateTime updDate
) {
    @Builder
    public BoardQueryResponse {}

    public static BoardQueryResponse of(Board board, User reg, Optional<User> updOpt, List<CommentQueryResponse> comments) {
        return BoardQueryResponse.builder()
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .comments(comments)
                .regId(board.getReg().getId())
                .regDate(board.getReg().getDt())
                .regUserName(reg.name())
                .updId(board.getUpdOpt().map(Upd::getId).orElse(null))
                .updDate(board.getUpdOpt().map(Upd::getDt).orElse(null))
                .updUserName(updOpt.map(User::name).orElse(null))
                .build();
    }
}