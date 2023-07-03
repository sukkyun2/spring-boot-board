package com.example.board.api.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Upd {
    @Column(name = "UPD_ID")
    private Integer id;
    @Column(name = "UPD_DT")
    private LocalDateTime dt;

    public static Upd of(Integer id){
        return new Upd(id, LocalDateTime.now());
    }
}
