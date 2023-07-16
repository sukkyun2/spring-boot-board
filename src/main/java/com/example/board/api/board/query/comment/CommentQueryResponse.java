package com.example.board.api.board.query.comment;

import com.example.board.api.board.domain.comment.Comment;
import com.example.board.api.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record CommentQueryResponse(
        CommentResponse comment,
        List<CommentResponse> subComments
) {
    @Builder
    public CommentQueryResponse {
    }

    public record CommentResponse(
            Integer commentId,
            String content,
            String regUserName,
            LocalDateTime regDate
    ) {
        @Builder
        public CommentResponse {
        }

        public static CommentResponse of(Comment comment, User reg) {
            return CommentResponse.builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .regUserName(reg.getName())
                    .regDate(comment.getReg().getDt())
                    .build();
        }
    }
}
