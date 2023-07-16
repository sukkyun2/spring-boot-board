package com.example.board.api.board.query.comment;

import com.example.board.api.board.app.NotExistBoardException;
import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardRepository;
import com.example.board.api.board.domain.comment.Comment;
import com.example.board.api.board.domain.comment.CommentRepository;
import com.example.board.api.common.domain.Reg;
import com.example.board.api.user.query.UserQueryService;
import com.example.board.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserQueryService userQueryService;

    public List<CommentQueryResponse> getCommentsByBoardSeq(Integer boardSeq){
        List<Comment> comments = findAllByBoardSeq(boardSeq);
        Map<Integer, User> userMap = userQueryService.getUserMapByUserIds(comments.stream()
                .map(Comment::getReg).map(Reg::getId).distinct()
                .toList());

        Map<Boolean, List<Comment>> partitionByRoot = comments.stream().collect(Collectors.partitioningBy(Comment::isRoot));
        Map<Integer, List<Comment>> groupingByUpperId = partitionByRoot.get(false).stream().collect(Collectors.groupingBy(Comment::getUpperCommentId));

        return partitionByRoot.get(true).stream()
                .map(comment -> CommentQueryResponse.builder()
                        .comment(CommentQueryResponse.CommentResponse.of(comment, userMap.get(comment.getReg().getId())))
                        .subComments(groupingByUpperId.getOrDefault(comment.getId(), Collections.emptyList()).stream()
                                .map(subComment -> CommentQueryResponse.CommentResponse.of(subComment, userMap.get(subComment.getReg().getId())))
                                .toList())
                        .build()
                ).toList();
    }

    private List<Comment> findAllByBoardSeq(Integer boardSeq){
        return Optional.ofNullable(boardRepository.findBySeq(boardSeq))
                .map(Board::getSeq)
                .map(commentRepository::findByBoardSeq)
                .orElseThrow(NotExistBoardException::new);
    }
}
