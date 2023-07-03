package com.example.board.api.board.api;

import com.example.board.api.board.app.BoardCreateRequest;
import com.example.board.api.board.app.BoardCreateService;
import com.example.board.api.common.api.ApiResponse;
import com.example.board.api.common.app.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardCreateApi {
    private final BoardCreateService boardCreateService;

    @PostMapping("/board")
    public ApiResponse<Void> createBoard(@RequestBody BoardCreateRequest req){
        try  {
            boardCreateService.createBoard(req);

            return ApiResponse.ok();
        } catch (ValidationException e){
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
