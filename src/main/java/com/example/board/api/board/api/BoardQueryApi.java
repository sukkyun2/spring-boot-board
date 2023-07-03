package com.example.board.api.board.api;

import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.query.BoardListQueryRequest;
import com.example.board.api.board.query.BoardListQueryService;
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

    @GetMapping("/board/{boardSeq}")
    public ApiResponse<?> getBoard(@PathVariable Integer boardSeq){
        try {
            return ApiResponse.ok(boardQueryService.getBoard(boardSeq));
        } catch (NotExistBoardException e){
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
