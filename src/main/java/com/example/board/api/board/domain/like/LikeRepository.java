package com.example.board.api.board.domain.like;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface LikeRepository extends Repository<Like, Integer> {

    Integer countAllByIdBoardSeqAndLikeType(Integer boardSeq, LikeType likeType);
    boolean existsById(LikeId id);
    void save(Like like);
}
