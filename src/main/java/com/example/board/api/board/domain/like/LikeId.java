package com.example.board.api.board.domain.like;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LikeId implements Serializable {
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "BOARD_SEQ")
    private Integer boardSeq;
}
