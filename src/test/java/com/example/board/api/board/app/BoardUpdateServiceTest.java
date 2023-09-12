package com.example.board.api.board.app;

import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.common.domain.BoardUser;
import com.example.board.util.MockBoardUserProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardUpdateServiceTest {

    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private BoardUpdateService boardUpdateService;

    @Test
    @DisplayName("게시글 수정시 없는 게시글이라면 예외발생")
    void given_not_exists_board_seq_then_throw_exception() {
        BoardUser boardUser = MockBoardUserProvider.getBoardUser();
        given(boardRepository.findBySeq(any())).willReturn(null);

        BoardUpdateRequest givenRequest = new BoardUpdateRequest(999, "제목", "내용");

        assertThrows(NotExistBoardException.class, () -> boardUpdateService.updateBoard(givenRequest, boardUser));
    }
}