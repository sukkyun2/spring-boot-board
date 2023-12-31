package com.example.board.api.board.app.comment;

import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.comment.Comment;
import com.example.board.api.board.domain.comment.CommentRepository;
import com.example.board.api.common.domain.BoardUser;
import com.example.board.api.common.domain.Reg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentCreateValidator validator = new CommentCreateValidator();

    public void addComment(CommentCreateRequest req, BoardUser boardUser) {
        validator.validate(req);

        if (!boardRepository.existsBySeq(req.boardSeq())) {
            throw new NotExistBoardException();
        }

        Comment comment = convert(req, boardUser);

        commentRepository.save(comment);
    }

    private Comment convert(CommentCreateRequest req, BoardUser boardUser) {
        return Comment.builder()
                .boardSeq(req.boardSeq())
                .upperCommentId(req.upperCommentId())
                .content(req.content())
                .reg(Reg.of(boardUser.getUserId()))
                .build();
    }
}
