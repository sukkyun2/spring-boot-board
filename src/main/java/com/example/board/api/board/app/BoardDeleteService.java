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
public class BoardDeleteService {
    private final BoardRepository boardRepository;

    @Transactional
    public void deleteBoard(BoardDeleteRequest req, BoardUser boardUser) {
        Board board = Optional.ofNullable(boardRepository.findBySeq(req.seq()))
                .orElseThrow(NotExistBoardException::new);

        board.delete(boardUser.getUserId());

        boardRepository.save(board);
    }
}
