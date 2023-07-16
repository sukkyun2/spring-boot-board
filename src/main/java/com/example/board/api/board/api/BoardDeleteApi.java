package com.example.board.api.board.api;

import com.example.board.api.board.app.*;
import com.example.board.api.common.api.ApiResponse;
import com.example.board.api.common.app.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardDeleteApi {
    private final BoardDeleteService boardDeleteService;

    @DeleteMapping("/api/board/{boardSeq}")
    public ApiResponse<Void> deleteBoard(@PathVariable Integer boardSeq,
                                         @RequestBody BoardDeleteRequest req) {
        try {
            boardDeleteService.deleteBoard(req);

            return ApiResponse.ok();
        } catch (ValidationException | NotExistBoardException e){
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
