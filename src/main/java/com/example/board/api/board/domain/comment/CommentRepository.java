package com.example.board.api.board.domain.comment;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface CommentRepository extends Repository<Comment, Integer> {
    Comment findById(Integer id);
    List<Comment> findByBoardSeq(Integer boardSeq);
    Comment save(Comment comment);
}
