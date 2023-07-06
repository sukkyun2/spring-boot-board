package com.example.board.api.board.query.like;

import com.example.board.api.board.domain.like.LikeRepository;
import com.example.board.api.board.domain.like.LikeSummary;
import com.example.board.api.board.domain.like.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeSummaryQueryService {
    private final LikeRepository likeRepository;

    public LikeSummary summarizingLike(Integer boardSeq){
        return new LikeSummary(
                likeRepository.countAllByIdBoardSeqAndLikeType(boardSeq, LikeType.LIKE),
                likeRepository.countAllByIdBoardSeqAndLikeType(boardSeq, LikeType.UNLIKE)
        );
    }
}
