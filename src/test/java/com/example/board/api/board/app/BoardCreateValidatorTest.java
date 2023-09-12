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
        BoardCreateRequest givenEmptyTitleReq = new BoardCreateRequest("","내용");

        assertThrows(
                ValidationException.class,
                () -> validator.validate(givenEmptyTitleReq),
                ERROR_MSG_TITLE_IS_EMPTY
        );
    }

    @Test
    @DisplayName("게시글 내용은 1글자 이상이어야 한다")
    void given_empty_content_then_throw_exception() {
        BoardCreateRequest givenEmptyContentReq = new BoardCreateRequest("제목","");

        assertThrows(
                ValidationException.class,
                () -> validator.validate(givenEmptyContentReq),
                ERROR_MSG_CONTENT_IS_EMPTY
        );
    }
}