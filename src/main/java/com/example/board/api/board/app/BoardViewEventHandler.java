package com.example.board.api.board.app;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardViewEventHandler {
    private final BoardUpdateService boardUpdateService;

    @EventListener
    public void increaseViewCount(BoardViewEvent event) {
        boardUpdateService.increaseViewCount(event.boardSeq());
    }
}
