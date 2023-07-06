package com.example.board.api.board.app.like;

import com.example.board.api.board.domain.like.LikeId;
import com.example.board.api.board.domain.like.LikeType;

public record LikeAddRequest(
        Integer userId,
        Integer boardSeq,
        LikeType likeType
){

    public LikeId getId() {
        return new LikeId(userId, boardSeq);
    }
}
