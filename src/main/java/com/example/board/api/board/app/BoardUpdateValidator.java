package com.example.board.api.board.app;

import com.example.board.api.common.app.ValidationException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.board.api.board.app.BoardValidateMessage.*;

public class BoardUpdateValidator {
    public void validate(BoardUpdateRequest req) {
        List<String> errors = new ArrayList<>();

        if(req.seq() == null){
            errors.add(ERROR_MSG_BOARD_SEQ_IS_NULL);
        }

        if (!StringUtils.hasText(req.title())) {
            errors.add(ERROR_MSG_TITLE_IS_EMPTY);
        }

        if (!StringUtils.hasText(req.content())) {
            errors.add(ERROR_MSG_CONTENT_IS_EMPTY);
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors.get(0));
        }
    }
}
