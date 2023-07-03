package com.example.board.api.board.domain;

import com.example.board.api.board.domain.comment.Comment;
import com.example.board.api.common.domain.Reg;
import com.example.board.api.common.domain.Upd;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@Table(name = "BOARD")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Integer seq;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "CONTENT")
    private String content;
    @OneToMany
    @JoinColumn(name = "BOARD_SEQ")
    private List<Comment> comments;
    @Enumerated(EnumType.STRING)
    @Column(name = "BOARD_STATUS")
    private BoardStatus boardStatus;
    @Embedded
    private Reg reg;
    @Embedded
    private Upd upd;

    @Builder
    public Board(String title, String content, Reg reg) {
        this.title = title;
        this.content = content;
        this.comments = new ArrayList<>();
        this.boardStatus = BoardStatus.NOT_DELETED;
        this.reg = reg;
    }

    public void update(String title, String content, Integer userId){
        this.title = title;
        this.content = content;
        this.upd = Upd.of(userId);
    }

    public void delete(Integer userId){
        this.boardStatus = BoardStatus.DELETED;
        this.upd = Upd.of(userId);
    }

    public boolean isNotDeleted(){
        return this.boardStatus == BoardStatus.NOT_DELETED;
    }

    public Optional<Upd> getUpdOpt(){
        return Optional.ofNullable(this.upd);
    }
}
