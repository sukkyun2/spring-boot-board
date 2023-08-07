package com.example.board.api.board.infra;

import com.example.board.api.board.domain.Board;

public interface ChatGPTCommentService {
    void addComment(Board board);
}
