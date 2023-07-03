package com.example.board.api.board.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends Repository<Board, Integer> {
    boolean existsBySeq(Integer seq);
    List<Board> findAllByBoardStatus(BoardStatus boardStatus, Pageable page);
    Board findBySeqAndBoardStatus(Integer seq, BoardStatus boardStatus);
    Board findBySeq(Integer seq);
    Board save(Board board);
}
