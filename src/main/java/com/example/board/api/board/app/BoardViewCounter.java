package com.example.board.api.board.app;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardViewCounter {
    private final BoardUpdateService boardUpdateService;

    @EventListener
    public void increaseViewCount(Integer boardSeq) {
        boardUpdateService.increaseViewCount(boardSeq);
    }
}
