package com.example.board.api.board.app.like;

import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.like.LikeId;
import com.example.board.api.board.domain.like.LikeRepository;
import com.example.board.api.board.domain.like.LikeType;
import com.example.board.api.common.app.ValidationException;
import com.example.board.api.common.domain.BoardUser;
import com.example.board.util.MockBoardUserProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.board.api.board.app.like.LikeAddValidateMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LikeAddValidatorTest {
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private LikeRepository likeRepository;
    @InjectMocks
    private LikeAddValidator validator;

    @Test
    @DisplayName("좋아요 클릭시 게시판 정보가 필요하다")
    void given_board_seq_null_then_throw_exception() {
        BoardUser boardUser = MockBoardUserProvider.getBoardUser();
        LikeAddRequest givenReq = new LikeAddRequest(1, LikeType.LIKE);

        ValidationException ex = assertThrows(ValidationException.class, () -> validator.validate(givenReq, boardUser));
        assertThat(ex).hasMessage(ERROR_MSG_BOARD_SEQ_IS_NULL);
    }

    @Test
    @DisplayName("게시판이 없을 경우 좋아요를 누를 수 없다")
    void given_not_exists_board_then_dont_add_like() {
        BoardUser boardUser = MockBoardUserProvider.getBoardUser();
        LikeAddRequest givenReq = new LikeAddRequest(1, LikeType.LIKE);
        given(boardRepository.existsBySeq(any())).willReturn(false);

        ValidationException ex = assertThrows(ValidationException.class, () -> validator.validate(givenReq, boardUser));
        assertThat(ex).hasMessage(ERROR_MSG_NOT_EXISTS_BOARD);
    }

    @Test
    @DisplayName("이미 좋아요를 누른 사용자는 중복하여 누를 수 없다")
    void given_user_added_like_then_dont_add_like() {
        BoardUser boardUser = MockBoardUserProvider.getBoardUser();
        LikeAddRequest givenReq = new LikeAddRequest(1, LikeType.LIKE);

        given(boardRepository.existsBySeq(any())).willReturn(true);
        given(likeRepository.existsById(new LikeId(boardUser.getUserId(), 2))).willReturn(true);

        ValidationException ex = assertThrows(ValidationException.class, () -> validator.validate(givenReq, boardUser));
        assertThat(ex).hasMessage(ERROR_MSG_ALREADY_ADDED_LIKE);
    }
}