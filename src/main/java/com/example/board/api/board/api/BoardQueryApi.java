package com.example.board.api.board.api;

import com.example.board.api.board.app.BoardViewEvent;
import com.example.board.api.board.app.BoardViewEventHandler;
import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.query.BoardQueryResponse;
import com.example.board.api.board.query.BoardQueryService;
import com.example.board.api.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardQueryApi {
    private final BoardQueryService boardQueryService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/api/board/{boardSeq}")
    public ApiResponse<?> getBoard(@PathVariable Integer boardSeq) {
        try {
            BoardQueryResponse board = boardQueryService.getBoard(boardSeq);
            publisher.publishEvent(new BoardViewEvent(boardSeq));

            return ApiResponse.ok(board);
        } catch (NotExistBoardException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
