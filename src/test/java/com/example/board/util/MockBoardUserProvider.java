package com.example.board.util;

import com.example.board.api.common.domain.BoardUser;

public class MockBoardUserProvider {
    public static BoardUser getBoardUser(){
        return new BoardUser(1, "사람", "127.0.0.1", false);
    }
}
