package com.example.board.api.board.infra;

import com.example.board.api.board.domain.Board;

import java.util.Objects;

public record ChatGPTCommentPayload(String commend, ChatGPTCommentPayloadData data) {

    public ChatGPTCommentPayload {
        Objects.requireNonNull(data.seq, "Board Seq is Not Null");
    }

    public ChatGPTCommentPayload(Board board) {
        this("request_chatgpt", new ChatGPTCommentPayloadData(board.getSeq(), board.getTitle(), board.getContent()));
    }

    record ChatGPTCommentPayloadData(Integer seq, String title, String content) {
    }
}
