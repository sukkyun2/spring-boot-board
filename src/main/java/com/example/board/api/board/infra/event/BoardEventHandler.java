package com.example.board.api.board.infra.event;

import com.example.board.api.board.app.BoardUpdateService;
import com.example.board.api.board.app.comment.AutoCommentAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardEventHandler {
    private final BoardUpdateService boardUpdateService;
    private final AutoCommentAddService autoCommentAddService;

    @EventListener
    public void increaseViewCount(BoardViewEvent event) {
        boardUpdateService.increaseViewCount(event.boardSeq());
    }

    @EventListener
    public void addComment(BoardCreateEvent event) {
        autoCommentAddService.addComment(event.board());
    }
}
