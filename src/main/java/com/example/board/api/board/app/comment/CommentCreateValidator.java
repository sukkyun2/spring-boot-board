package com.example.board.api.board.app.comment;

import com.example.board.api.common.app.ValidationException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.board.api.board.app.comment.CommentCreateValidateMessage.ERROR_MSG_BOARD_SEQ_IS_NULL;
import static com.example.board.api.board.app.comment.CommentCreateValidateMessage.ERROR_MSG_COMMENT_CONTENT_IS_EMPTY;

public class CommentCreateValidator {

    public void validate(CommentCreateRequest req) {
        List<String> errors = new ArrayList<>();

        if (req.boardSeq() == null) {
            errors.add(ERROR_MSG_BOARD_SEQ_IS_NULL);
        }

        if (!StringUtils.hasText(req.content())) {
            errors.add(ERROR_MSG_COMMENT_CONTENT_IS_EMPTY);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors.get(0));
        }

    }
}
