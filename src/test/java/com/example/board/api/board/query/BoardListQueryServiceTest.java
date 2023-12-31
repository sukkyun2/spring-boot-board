package com.example.board.api.board.query;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.common.domain.Reg;
import com.example.board.api.user.query.UserQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class BoardListQueryServiceTest {
    @Mock
    private UserQueryService userQueryService;
    @Autowired
    private BoardRepository boardRepository;
    private BoardListQueryService boardListQueryService;

    @BeforeEach
    void setUp() {
        boardListQueryService = new BoardListQueryService(boardRepository, userQueryService);
    }

    @Test
    @DisplayName("조회시 삭제된 게시글은 조회되지 않는다")
    void given_deleted_board_when_select_board_then_return_null() {
        givenDeletedBoard();

        List<BoardListQueryResponse> boardList = boardListQueryService.getBoardList(new BoardListQueryRequest(0, 10));
        assertThat(boardList).isEmpty();
    }

    private void givenDeletedBoard() {
        Board givenBoard = Board.builder().title("제목").content("내용").reg(Reg.of(1)).build();
        givenBoard.delete(1);

        boardRepository.save(givenBoard);
    }

}