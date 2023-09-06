package com.example.board.doc.board;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.BoardDeleteApi;
import com.example.board.api.board.app.BoardDeleteService;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardDeleteApiDoc extends ApiDocBase {
    @Mock
    private BoardDeleteService boardDeleteService;
    @InjectMocks
    private BoardDeleteApi boardDeleteApi;

    @Override
    public Object getApi() {
        return boardDeleteApi;
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteBoard() throws Exception {
        mockMvc.perform(delete("/api/board/{boardSeq}", 1)
                        .content("""
                                    {
                                        "seq" : 3,
                                        "userId" : 3
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("delete-board",
                        requestCookies(cookieWithName("token").description("토큰")),
                        pathParameters(parameterWithName("boardSeq").description("게시글 고유번호")),
                        requestFields(
                                fieldWithPath("seq").type(NUMBER).description("게시글 고유번호"),
                                fieldWithPath("userId").type(NUMBER).description("게시글 삭제 유저 ID")
                        ),
                        responseFields(apiObjectResponseFieldDescriptor())));
    }
}