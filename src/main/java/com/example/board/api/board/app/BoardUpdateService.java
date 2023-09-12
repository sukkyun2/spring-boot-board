package com.example.board.api.board.app;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.common.domain.BoardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardUpdateService {
    private final BoardRepository boardRepository;
    private final BoardUpdateValidator validator = new BoardUpdateValidator();

    @Transactional
    public void updateBoard(BoardUpdateRequest req, BoardUser boardUser) {
        validator.validate(req);

        Board board = Optional.ofNullable(boardRepository.findBySeq(req.seq()))
                .orElseThrow(NotExistBoardException::new);

        board.update(req.title(), req.content(), boardUser.getUserId());

        boardRepository.save(board);
    }

    @Transactional
    public void increaseViewCount(Integer boardSeq) {
        Board board = boardRepository.findBySeq(boardSeq);

        if (board == null) return;

        board.increaseViewCount();
    }
}
