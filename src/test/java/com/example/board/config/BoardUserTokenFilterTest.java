package com.example.board.config;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.query.BoardQueryService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BoardQueryService.class)
class BoardUserTokenFilterTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BoardQueryService boardQueryService;

    @Test
    @DisplayName("Filter 패턴에 걸리지 않으면 토큰체크 X")
    void when_not_matched_uri_pattern_then_dont_check_token() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is(not(HttpServletResponse.SC_UNAUTHORIZED)));
    }

    @Test
    @DisplayName("Filter 패턴에 걸렸을 때 토큰이 없으면 statusCode 401")
    void given_not_exist_token_when_matched_uri_patten_then_return_401() throws Exception {
        mockMvc.perform(get("/api/board/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Filter 패턴에 걸렸을때 유효한 토큰이면 statusCode 200")
    void given_exist_token_when_matched_uri_pattern_then_return_200() throws Exception {
        String givenToken = TokenGenerator.gen(1, "사람");

        mockMvc.perform(get("/api/board/1")
                        .header("token", givenToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Filter 패턴에 걸렸을때 만료된 토큰이면 statusCode 401")
    void given_expired_token_when_matched_uri_pattern_then_return_401() throws Exception {
        String givenToken = TokenGenerator.gen(1, "사람", LocalDateTime.now().minusDays(1));

        mockMvc.perform(get("/api/board/1")
                        .header("token", givenToken))
                .andExpect(status().isUnauthorized());
    }
}