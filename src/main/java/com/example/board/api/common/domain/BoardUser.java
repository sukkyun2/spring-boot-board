package com.example.board.api.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUser {
    private Integer userId;
    private String userName;
    private String userIp;
    private boolean expired;
}
