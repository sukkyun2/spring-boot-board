package com.example.board.api.board.app.comment;

import lombok.Builder;

public record CommentCreateRequest(
        Integer boardSeq,
        Integer upperCommentId,
        String content,
        Integer userId
) {
    @Builder
    public CommentCreateRequest {
    }
}
