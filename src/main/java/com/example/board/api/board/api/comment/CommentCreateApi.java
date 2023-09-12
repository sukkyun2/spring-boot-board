package com.example.board.api.board.api.comment;

import com.example.board.api.board.app.comment.CommentCreateRequest;
import com.example.board.api.board.app.comment.CommentCreateService;
import com.example.board.api.common.api.ApiResponse;
import com.example.board.api.common.app.ValidationException;
import com.example.board.api.common.domain.BoardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentCreateApi {
    private final CommentCreateService commentCreateService;

    @PostMapping("/api/board/{boardSeq}/comments")
    public ApiResponse<Void> addComment(@PathVariable String boardSeq,
                                        @RequestBody CommentCreateRequest req,
                                        BoardUser boardUser) {
        try {
            commentCreateService.addComment(req, boardUser);

            return ApiResponse.ok();
        } catch (ValidationException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}