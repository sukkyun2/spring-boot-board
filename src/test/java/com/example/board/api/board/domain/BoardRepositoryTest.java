package com.example.board.api.board.domain;

import com.example.board.api.common.domain.Reg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 작성")
    void create_board() {
        Integer seq = givenBoard();

        Board board = boardRepository.findBySeq(seq);
        assertThat(board.getSeq()).isEqualTo(seq);
        assertThat(board.getTitle()).isEqualTo("제목");
        assertThat(board.getContent()).isEqualTo("내용");
        assertThat(board.getComments()).isEmpty();
        assertThat(board.getReg().getId()).isEqualTo(1);
        assertThat(board.getReg().getDt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 수정")
    void update_board() {
        Board givenBoard = Optional.of(givenBoard()).map(seq -> boardRepository.findBySeq(seq)).orElseThrow();

        givenBoard.update("제목변경", "내용변경", 2);
        boardRepository.save(givenBoard);


        Board board = boardRepository.findBySeq(givenBoard.getSeq());
        assertThat(board.getSeq()).isEqualTo(givenBoard.getSeq());
        assertThat(board.getTitle()).isEqualTo("제목변경");
        assertThat(board.getContent()).isEqualTo("내용변경");
        assertThat(board.getComments()).isEmpty();
        assertThat(board.getUpd().getId()).isEqualTo(2);
        assertThat(board.getUpd().getDt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete_board() {
        Board givenBoard = Optional.of(givenBoard()).map(seq -> boardRepository.findBySeq(seq)).orElseThrow();

        givenBoard.delete(2);
        boardRepository.save(givenBoard);

        Board board = boardRepository.findBySeq(givenBoard.getSeq());
        assertThat(board.getSeq()).isEqualTo(givenBoard.getSeq());
        assertThat(board.getBoardStatus()).isEqualTo(BoardStatus.DELETED);
    }

    private Integer givenBoard() {
        Board givenBoard = Board.builder().title("제목").content("내용").reg(Reg.of(1)).build();
        Board savedBoard = boardRepository.save(givenBoard);

        return savedBoard.getSeq();
    }

}