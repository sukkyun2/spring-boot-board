package com.example.board.api.board.app.like;

import com.example.board.api.board.domain.like.Like;
import com.example.board.api.board.domain.like.LikeRepository;
import com.example.board.api.common.domain.BoardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeAddService {
    private final LikeAddValidator validator;
    private final LikeRepository likeRepository;

    @Transactional
    public void addLike(LikeAddRequest req, BoardUser boardUser) {
        validator.validate(req, boardUser);

        likeRepository.save(Like.builder()
                .userId(boardUser.getUserId())
                .boardSeq(req.boardSeq())
                .likeType(req.likeType())
                .build());
    }
}
