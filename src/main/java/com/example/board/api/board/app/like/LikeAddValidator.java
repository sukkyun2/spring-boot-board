package com.example.board.api.board.app.like;

import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.like.LikeId;
import com.example.board.api.board.domain.like.LikeRepository;
import com.example.board.api.common.app.ValidationException;
import com.example.board.api.common.domain.BoardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.board.api.board.app.like.LikeAddValidateMessage.*;

@Component
@RequiredArgsConstructor
public class LikeAddValidator {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    public void validate(LikeAddRequest req, BoardUser boardUser) {
        List<String> errors = new ArrayList<>();

        if (req.boardSeq() == null) {
            errors.add(ERROR_MSG_BOARD_SEQ_IS_NULL);
        }

        if (!boardRepository.existsBySeq(req.boardSeq())) {
            errors.add(ERROR_MSG_NOT_EXISTS_BOARD);
        }

        LikeId likeId = new LikeId(boardUser.getUserId(), req.boardSeq());

        if (likeRepository.existsById(likeId)) {
            errors.add(ERROR_MSG_ALREADY_ADDED_LIKE);
        }

        if (errors.size() > 0) {
            throw new ValidationException(errors.get(0));
        }
    }
}
