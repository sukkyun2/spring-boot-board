package com.example.board.api.board.app.like;

import com.example.board.api.board.domain.like.LikeType;

public record LikeAddRequest(Integer boardSeq, LikeType likeType) {
}
