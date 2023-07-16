package com.example.board.api.board.query;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.BoardStatus;
import com.example.board.api.user.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardListQueryService {
    private final BoardRepository boardRepository;
    private final UserQueryService userQueryService;

    public List<BoardListQueryResponse> getBoardList(BoardListQueryRequest req){
        return this.findAll(req.getPageable()).stream()
                .map(board -> BoardListQueryResponse.of(board, userQueryService.getUserByUserId(board.getReg().getId())))
                .toList();
    }

    private List<Board> findAll(Pageable page){
        return boardRepository.findAllByBoardStatus(BoardStatus.NOT_DELETED, page);
    }
}
