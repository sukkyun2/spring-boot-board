package com.example.board.api.board.query.comment;

import com.example.board.api.board.domain.comment.Comment;
import com.example.board.api.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentQueryResponse {
    private CommentResponse comment;
    private List<CommentResponse> subComments;

    @Getter
    @Builder
    public static class CommentResponse {
        private Integer commentId;
        private String content;
        private String regUserName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime regDate;

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
