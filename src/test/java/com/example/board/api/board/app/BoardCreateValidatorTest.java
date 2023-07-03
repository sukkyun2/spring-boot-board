package com.example.board.api.board.app;

import com.example.board.api.common.app.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.board.api.board.app.BoardValidateMessage.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardCreateValidatorTest {

    private BoardCreateValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BoardCreateValidator();
    }

    @Test
    @DisplayName("게시글 제목은 1글자 이상이어야 한다")
    void given_empty_title_then_throw_exception() {
        BoardCreateRequest givenEmptyTitleReq = BoardCreateRequest.builder().title("").build();

        assertThrows(
                ValidationException.class,
                () -> validator.validate(givenEmptyTitleReq),
                ERROR_MSG_TITLE_IS_EMPTY
        );
    }

    @Test
    @DisplayName("게시글 제목은 1글자 이상이어야 한다")
    void given_empty_content_then_throw_exception() {
        BoardCreateRequest givenEmptyContentReq = BoardCreateRequest.builder().title("제목").content(null).build();

        assertThrows(
                ValidationException.class,
                () -> validator.validate(givenEmptyContentReq),
                ERROR_MSG_CONTENT_IS_EMPTY
        );
    }

    @Test
    @DisplayName("게시글 생성시 작성자 정보가 필요하다")
    void given_user_id_null_then_throw_exception() {
        BoardCreateRequest givenUserIdIsNullReq = BoardCreateRequest.builder().title("제목").content("내용").userId(null).build();

        assertThrows(
                ValidationException.class,
                () -> validator.validate(givenUserIdIsNullReq),
                ERROR_MSG_USER_ID_IS_NULL
        );
    }

}