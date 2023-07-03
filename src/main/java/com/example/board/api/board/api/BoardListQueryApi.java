package com.example.board.api.board.api;

import com.example.board.api.board.query.BoardListQueryRequest;
import com.example.board.api.board.query.BoardListQueryResponse;
import com.example.board.api.board.query.BoardListQueryService;
import com.example.board.api.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardListQueryApi {
    private final BoardListQueryService boardListQueryService;

    @GetMapping("/board")
    public ApiResponse<List<BoardListQueryResponse>> getBoardList(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return ApiResponse.ok(
                boardListQueryService.getBoardList(new BoardListQueryRequest(offset, limit))
        );
    }
}
