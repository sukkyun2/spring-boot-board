package com.example.board.api.board.api.comment;

import com.example.board.api.board.query.comment.CommentQueryResponse;
import com.example.board.api.board.query.comment.CommentQueryService;
import com.example.board.api.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentQueryApi {
    private final CommentQueryService commentQueryService;

    @GetMapping("/board/{boardSeq}/comments")
    public ApiResponse<List<CommentQueryResponse>> getComments(@PathVariable Integer boardSeq){
        return ApiResponse.ok(commentQueryService.getCommentsByBoardSeq(boardSeq));
    }
}
