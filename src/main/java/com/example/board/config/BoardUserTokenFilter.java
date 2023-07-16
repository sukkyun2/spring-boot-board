package com.example.board.config;

import com.example.board.api.auth.query.TokenExtractor;
import com.example.board.api.common.domain.BoardUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class BoardUserTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        BoardUser user = TokenExtractor.extract(request);

        if (user == null) {
            log.error("illegal access");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        if (user.isExpired()) {
            log.error("token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        doFilter(request, response, filterChain);
    }
}
