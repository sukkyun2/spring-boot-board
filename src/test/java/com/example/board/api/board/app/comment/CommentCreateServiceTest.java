package com.example.board.api.board.app.comment;

import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.comment.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentCreateServiceTest {

    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private CommentCreateService commentCreateService;

    @Test
    @DisplayName("존재하지않는 게시물에 댓글작성시 예외발생")
    void given_not_exists_board_seq_then_throw_exception() {
        given(boardRepository.existsBySeq(eq(1))).willReturn(false);
        CommentCreateRequest givenReq = CommentCreateRequest.builder().boardSeq(1).upperCommentId(null).content("댓글작성").userId(1).build();

        assertThrows(
                NotExistBoardException.class,
                ()-> commentCreateService.addComment(givenReq)
        );
    }


}