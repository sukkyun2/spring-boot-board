package com.example.board.api.board.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record BoardListQueryRequest(
        int offset,
        int limit
) {

    public Pageable getPageable() {
        return PageRequest.of(offset, limit);
    }
}
