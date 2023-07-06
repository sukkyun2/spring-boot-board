package com.example.board.api.board.api;

import com.example.board.api.board.app.BoardViewCounter;
import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.query.BoardListQueryRequest;
import com.example.board.api.board.query.BoardListQueryService;
import com.example.board.api.board.query.BoardQueryResponse;
import com.example.board.api.board.query.BoardQueryService;
import com.example.board.api.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardQueryApi {
    private final BoardQueryService boardQueryService;
    private final BoardViewCounter boardViewCounter;

    @GetMapping("/board/{boardSeq}")
    public ApiResponse<?> getBoard(@PathVariable Integer boardSeq) {
        try {
            BoardQueryResponse board = boardQueryService.getBoard(boardSeq);
            boardViewCounter.increaseViewCount(boardSeq);

            return ApiResponse.ok(board);
        } catch (NotExistBoardException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
