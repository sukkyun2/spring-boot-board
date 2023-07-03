package com.example.board.api.board.domain.comment;

import com.example.board.api.common.domain.Reg;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "COMMENT")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "BOARD_SEQ")
    private Integer boardSeq;
    @Column(name = "UPPER_COMMENT_ID")
    private Integer upperCommentId;
    @Column(name = "CONTENT")
    private String content;
    @Embedded
    private Reg reg;

    public boolean isRoot(){
        return upperCommentId == null;
    }
}
