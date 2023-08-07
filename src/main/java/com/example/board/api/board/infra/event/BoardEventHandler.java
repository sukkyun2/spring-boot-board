package com.example.board.api.board.infra.event;

import com.example.board.api.board.app.BoardUpdateService;
import com.example.board.api.board.domain.Board;
import com.example.board.api.board.infra.ChatGPTCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardEventHandler {
    private final BoardUpdateService boardUpdateService;
    private final ChatGPTCommentService chatGPTCommentService;

    @EventListener
    public void increaseViewCount(BoardViewEvent event) {
        boardUpdateService.increaseViewCount(event.boardSeq());
    }

    @EventListener
    public void addComment(BoardCreateEvent event) {
        chatGPTCommentService.addComment(event.board());
    }
}
