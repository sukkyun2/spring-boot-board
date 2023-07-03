package com.example.board.api.board.app.comment;

import com.example.board.api.common.app.ValidationException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentCreateValidator {

    public void validate(CommentCreateRequest req){
        List<String> errors = new ArrayList<>();

        if(req.boardSeq() == null){
            errors.add("board seq is not null");
        }

        if(!StringUtils.hasText(req.content())){
            errors.add("1글자이상의 댓글내용을 작성해주세요");
        }

        if(req.userId() == null){
            errors.add("userId is not null");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors.get(0));
        }

    }
}
