package com.example.board.api.board.domain.comment;

import com.example.board.api.common.domain.Reg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 작성")
    @Sql("/insert_board.sql")
    void add_comment(){
        Comment givenComment = Comment.builder().boardSeq(1).upperCommentId(null).content("댓글내용").reg(Reg.of(1)).build();

        Comment save = commentRepository.save(givenComment);

        Comment byId = commentRepository.findById(save.getId());
        assertThat(byId.getId()).isNotNull();
        assertThat(byId.getContent()).isEqualTo("댓글내용");
        assertThat(byId.getBoardSeq()).isEqualTo(1);
        assertThat(byId.getReg().getId()).isEqualTo(1);
        assertThat(byId.getReg().getDt()).isNotNull();
    }
}