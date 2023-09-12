package com.example.board.api.board.api;

import com.example.board.api.board.app.BoardUpdateRequest;
import com.example.board.api.board.app.BoardUpdateService;
import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.common.api.ApiResponse;
import com.example.board.api.common.app.ValidationException;
import com.example.board.api.common.domain.BoardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardUpdateApi {
    private final BoardUpdateService boardUpdateService;

    @PutMapping("/api/board/{boardSeq}")
    public ApiResponse<Void> updateBoard(@PathVariable Integer boardSeq,
                                         @RequestBody BoardUpdateRequest req,
                                         BoardUser boardUser) {
        try {
            boardUpdateService.updateBoard(req, boardUser);

            return ApiResponse.ok();
        } catch (ValidationException | NotExistBoardException e) {
            return ApiResponse.badRequest(e.getMessage());
        }

    }
}
