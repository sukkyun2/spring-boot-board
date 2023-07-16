package com.example.board.api.board.query;

import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.BoardStatus;
import com.example.board.api.board.domain.like.LikeSummary;
import com.example.board.api.board.query.comment.CommentQueryService;
import com.example.board.api.board.query.like.LikeSummaryQueryService;
import com.example.board.api.common.domain.Upd;
import com.example.board.api.user.query.UserQueryService;
import com.example.board.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class BoardQueryService {
    private final BoardRepository boardRepository;
    private final UserQueryService userQueryService;
    private final CommentQueryService commentQueryService;
    private final LikeSummaryQueryService likeSummaryQueryService;

    public BoardQueryResponse getBoard(Integer seq) {
        return ofNullable(this.findBySeq(seq))
                .map(this::getBoardInternal)
                .orElseThrow(NotExistBoardException::new);
    }

    private BoardQueryResponse getBoardInternal(Board board) {
        User reg = userQueryService.getUserByUserId(board.getReg().getId());
        Optional<User> upd = board.getUpdOpt().map(Upd::getId).map(userQueryService::getUserByUserId);

        LikeSummary likeSummary = likeSummaryQueryService.summarizingLike(board.getSeq());

        return BoardQueryResponse.of(board, reg, upd, likeSummary);
    }

    private Board findBySeq(Integer seq) {
        return boardRepository.findBySeqAndBoardStatus(seq, BoardStatus.NOT_DELETED);
    }
}
