package com.example.board.api.board.app;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.common.domain.Reg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardCreateService {
    private final BoardRepository boardRepository;
    private final BoardCreateValidator validator = new BoardCreateValidator();

    @Transactional
    public Board createBoard(BoardCreateRequest req) {
        validator.validate(req);
        Board board = convert(req);

        return boardRepository.save(board);
    }

    private Board convert(BoardCreateRequest req) {
        return Board.builder()
                .title(req.title())
                .content(req.content())
                .reg(Reg.of(req.userId()))
                .build();
    }
}
