package com.example.board.api.board.api.like;

import com.example.board.api.board.app.like.LikeAddRequest;
import com.example.board.api.board.app.like.LikeAddService;
import com.example.board.api.common.api.ApiResponse;
import com.example.board.api.common.app.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeAddApi {
    private final LikeAddService likeAddService;

    @PostMapping("/api/board/{boardSeq}/likes")
    public ApiResponse<Void> addComment(@PathVariable Integer boardSeq,
                                        @RequestBody LikeAddRequest req) {
        try {
            likeAddService.addLike(req);

            return ApiResponse.ok();
        } catch (ValidationException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}