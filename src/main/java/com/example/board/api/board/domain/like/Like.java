package com.example.board.api.board.domain.like;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "BOARD_LIKE")
public class Like {
    @EmbeddedId
    private LikeId id;
    @Enumerated(EnumType.STRING)
    @Column(name = "LIKE_TYPE")
    private LikeType likeType;
    @Column(name = "REG_DT")
    private LocalDateTime regDate;

    @Builder
    public Like(Integer userId, Integer boardSeq, LikeType likeType) {
        this.id = new LikeId(userId, boardSeq);
        this.likeType = likeType;
        this.regDate = LocalDateTime.now();
    }
}
