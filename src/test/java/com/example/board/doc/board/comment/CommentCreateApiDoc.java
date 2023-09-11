package com.example.board.doc.board.comment;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.comment.CommentCreateApi;
import com.example.board.api.board.app.comment.CommentCreateService;
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

class CommentCreateApiDoc extends ApiDocBase {
    @Mock
    private CommentCreateService commentCreateService;
    @InjectMocks
    private CommentCreateApi commentCreateApi;

    @Override
    public Object getApi() {
        return commentCreateApi;
    }

    @Test
    @DisplayName("댓글 등록")
    public void createComment() throws Exception {
        mockMvc.perform(post("/api/board/{boardSeq}/comments", 1)
                        .content("""
                                    {
                                        "boardSeq" : 1,
                                        "upperCommentId" : 1,
                                        "content" : "대댓글!",
                                        "userId" : 2
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("create-comment",
                        requestCookies(cookieWithName("token").description("토큰")),
                        requestFields(
                                fieldWithPath("boardSeq").type(NUMBER).description("게시글 고유번호"),
                                fieldWithPath("upperCommentId").optional().type(NUMBER).description("상위 댓글 고유번호|없으면 NULL"),
                                fieldWithPath("content").type(STRING).description("댓글 내용"),
                                fieldWithPath("userId").type(NUMBER).description("댓글 등록자 고유번호")
                        ),
                        responseFields(apiObjectResponseFieldDescriptor())));
    }
}