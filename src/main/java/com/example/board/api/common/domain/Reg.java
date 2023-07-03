package com.example.board.api.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Reg {
    @Column(name = "REG_ID")
    private Integer id;
    @Column(name = "REG_DT")
    private LocalDateTime dt;

    public static Reg of(Integer id) {
        return new Reg(id, LocalDateTime.now());
    }
}
