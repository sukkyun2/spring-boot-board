package com.example.board.api.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardUser {
    private Integer userId;
    private String userName;
    private String userIp;
    private boolean expired;
}
