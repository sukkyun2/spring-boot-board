package com.example.board.api.board.query;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.like.LikeSummary;
import com.example.board.api.common.domain.Upd;
import com.example.board.api.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Builder
public class BoardQueryResponse {
    private Integer seq;
    private String title;
    private String content;
    private Integer likeCount;
    private Integer unlikeCount;
    private Integer viewCount;
    private Integer regId;
    private String regUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    private Integer updId;
    private String updUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updDate;

    public static BoardQueryResponse of(Board board, User reg, Optional<User> updOpt, LikeSummary likeSummary) {
        return BoardQueryResponse.builder()
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(likeSummary.getLikeCount())
                .unlikeCount(likeSummary.getUnlikeCount())
                .viewCount(board.getViewCount())
                .regId(board.getReg().getId())
                .regDate(board.getReg().getDt())
                .regUserName(reg.getName())
                .updId(board.getUpdOpt().map(Upd::getId).orElse(null))
                .updDate(board.getUpdOpt().map(Upd::getDt).orElse(null))
                .updUserName(updOpt.map(User::getName).orElse(null))
                .build();
    }
}