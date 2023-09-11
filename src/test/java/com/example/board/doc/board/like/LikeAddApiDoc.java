package com.example.board.doc.board.like;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.like.LikeAddApi;
import com.example.board.api.board.app.like.LikeAddService;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeAddApiDoc extends ApiDocBase {
    @Mock
    private LikeAddService likeAddService;
    @InjectMocks
    private LikeAddApi likeAddApi;

    @Test
    @DisplayName("좋아요/싫어요 등록")
    public void addLike() throws Exception {
        mockMvc.perform(post("/api/board/{boardSeq}/likes", 1)
                        .content("""
                                    {
                                        "likeType" : "LIKE",
                                        "boardSeq" : 1,
                                        "userId" : 2
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("add-like",
                        requestCookies(cookieWithName("token").description("토큰")),
                        requestFields(
                                fieldWithPath("likeType").type(STRING).description("선호타입|LIKE: 좋아요, UNLIKE: 싫어요"),
                                fieldWithPath("boardSeq").type(NUMBER).description("게시글 고유번호"),
                                fieldWithPath("userId").type(NUMBER).description("댓글 등록자 고유번호")
                        ),
                        responseFields(apiObjectResponseFieldDescriptor())));
    }

    @Override
    public Object getApi() {
        return likeAddApi;
    }
}