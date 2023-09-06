package com.example.board.doc.board;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.BoardUpdateApi;
import com.example.board.api.board.app.BoardUpdateService;
import com.example.board.doc.ApiDocBase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardUpdateApiDoc extends ApiDocBase {
    @Mock
    private BoardUpdateService boardUpdateService;
    @InjectMocks
    private BoardUpdateApi boardUpdateApi;

    @Override
    public Object getApi() {
        return boardUpdateApi;
    }

    @Test
    @DisplayName("게시글 수정")
    public void updateBoard() throws Exception {
        mockMvc.perform(put("/api/board/{boardSeq}", 1)
                        .content("""
                                    {
                                        "seq" : 3,
                                        "title" : "수정된 게시글",
                                        "content" : "내용",
                                        "userId" : 2
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("update-board",
                        requestCookies(cookieWithName("token").description("토큰")),
                        pathParameters(parameterWithName("boardSeq").description("게시글 고유번호")),
                        requestFields(
                                fieldWithPath("seq").type(NUMBER).description("게시글 고유번호"),
                                fieldWithPath("title").type(STRING).description("게시글 제목"),
                                fieldWithPath("content").type(STRING).description("게시글 내용"),
                                fieldWithPath("userId").type(NUMBER).description("게시글 등록자 ID")
                        ),
                        responseFields(apiObjectResponseFieldDescriptor())));
    }
}