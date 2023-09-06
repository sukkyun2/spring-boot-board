package com.example.board.doc.board;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.BoardCreateApi;
import com.example.board.api.board.app.BoardCreateService;
import com.example.board.doc.ApiDocBase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardCreateApiDoc extends ApiDocBase {
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private BoardCreateService boardCreateService;
    @InjectMocks
    private BoardCreateApi boardCreateApi;

    @Override
    public Object getApi() {
        return boardCreateApi;
    }

    @Test
    @DisplayName("게시글 등록")
    public void createBoard() throws Exception {
        mockMvc.perform(post("/api/board")
                        .content("""
                                {
                                    "title" : "새로운 게시글",
                                    "content" : "내용",
                                    "userId" : 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("create-board",
                        requestCookies(cookieWithName("token").description("토큰")),
                        requestFields(
                                fieldWithPath("title").type(STRING).description("게시글 제목"),
                                fieldWithPath("content").type(STRING).description("게시글 내용"),
                                fieldWithPath("userId").type(NUMBER).description("게시글 등록자 ID")
                        ),
                        responseFields(apiObjectResponseFieldDescriptor())));
    }
}