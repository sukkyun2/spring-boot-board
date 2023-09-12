package com.example.board.api.board.app.comment;

public record CommentCreateRequest(Integer boardSeq, Integer upperCommentId, String content) {
}
