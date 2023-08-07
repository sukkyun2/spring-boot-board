package com.example.board.api.board.infra.event;

import com.example.board.api.board.domain.Board;

public record BoardCreateEvent(
        Board board
) {
}
