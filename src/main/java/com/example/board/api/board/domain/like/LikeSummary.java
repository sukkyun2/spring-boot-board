package com.example.board.api.board.domain.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeSummary {
    private Integer likeCount;
    private Integer unlikeCount;
}
