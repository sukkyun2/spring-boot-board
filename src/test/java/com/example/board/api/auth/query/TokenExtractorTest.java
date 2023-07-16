package com.example.board.api.auth.query;

import com.example.board.api.common.domain.BoardUser;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TokenExtractorTest {

    @Test
    @DisplayName("만료되지 않은 토큰으로 접근시")
    void extract() {
        String givenToken = TokenGenerator.gen(1, "사람");
        HttpServletRequest givenHttpServletRequest = givenHttpServletRequestWithToken(givenToken);

        BoardUser boardUser = TokenExtractor.extract(givenHttpServletRequest);

        assertThat(boardUser).isNotNull();
        assertThat(boardUser.getUserId()).isEqualTo(1);
        assertThat(boardUser.getUserName()).isEqualTo("사람");
        assertThat(boardUser.isExpired()).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰으로 접근시")
    void given_expired_token_then_expired_true() {
        String givenToken = TokenGenerator.gen(1, "사람", LocalDateTime.now().minusDays(1));
        HttpServletRequest givenHttpServletRequest = givenHttpServletRequestWithToken(givenToken);

        BoardUser boardUser = TokenExtractor.extract(givenHttpServletRequest);

        assertThat(boardUser).isNotNull();
        assertThat(boardUser.getUserId()).isEqualTo(1);
        assertThat(boardUser.getUserName()).isEqualTo("사람");
        assertThat(boardUser.isExpired()).isTrue();
    }

    @Test
    @DisplayName("잘못된 토큰형식으로 접근시 NULL")
    void given_wrong_token_then_return_null() {
        String givenToken = "wrong-token";
        HttpServletRequest givenHttpServletRequest = givenHttpServletRequestWithToken(givenToken);

        BoardUser boardUser = TokenExtractor.extract(givenHttpServletRequest);

        assertThat(boardUser).isNull();
    }

    @Test
    @DisplayName("헤더에 토큰 없이 접근시 NULL")
    void given_not_exist_token_in_header_then_return_null() {
        HttpServletRequest givenHttpServletRequest = new MockHttpServletRequest();

        BoardUser boardUser = TokenExtractor.extract(givenHttpServletRequest);

        assertThat(boardUser).isNull();
    }

    private HttpServletRequest givenHttpServletRequestWithToken(String token){
        MockHttpServletRequest givenHttpServletRequest = new MockHttpServletRequest();
        givenHttpServletRequest.addHeader("token", token);

        return givenHttpServletRequest;
    }
}