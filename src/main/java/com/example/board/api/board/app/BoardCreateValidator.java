package com.example.board.api.board.app;

import com.example.board.api.common.app.ValidationException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.board.api.board.app.BoardValidateMessage.ERROR_MSG_CONTENT_IS_EMPTY;
import static com.example.board.api.board.app.BoardValidateMessage.ERROR_MSG_TITLE_IS_EMPTY;

public class BoardCreateValidator {
    public void validate(BoardCreateRequest req) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasText(req.title())) {
            errors.add(ERROR_MSG_TITLE_IS_EMPTY);
        }

        if (!StringUtils.hasText(req.content())) {
            errors.add(ERROR_MSG_CONTENT_IS_EMPTY);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors.get(0));
        }
    }
}
