package com.example.board.doc.board.comment;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.comment.CommentQueryApi;
import com.example.board.api.board.query.comment.CommentQueryResponse;
import com.example.board.api.board.query.comment.CommentQueryService;
import com.example.board.doc.ApiDocBase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentQueryApiDoc extends ApiDocBase {
    @Mock
    private CommentQueryService commentQueryService;
    @InjectMocks
    private CommentQueryApi commentQueryApi;

    @Override
    public Object getApi() {
        return commentQueryApi;
    }

    @Test
    @DisplayName("게시판 댓글 조회")
    void getComments() throws Exception {
        givenComments();

        mockMvc.perform(get("/api/board/{boardSeq}/comments", 1)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("get-comment-list",
                        requestCookies(cookieWithName("token").description("토큰")),
                        pathParameters(parameterWithName("boardSeq").description("게시글 고유번호")),
                        responseFields(apiArrayResponseFieldDescriptor())
                                .andWithPrefix("data[].",
                                        fieldWithPath("comment").type(OBJECT).description("댓글 리스트"),
                                        fieldWithPath("subComments").type(ARRAY).description("대댓글 리스트")
                                )
                                .andWithPrefix("data[].comment.", getCommentFieldDescriptor())
                                .andWithPrefix("data[].subComments[].", getCommentFieldDescriptor())
                ));
    }

    private FieldDescriptor[] getCommentFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("commentId").type(NUMBER).description("댓글 고유번호"),
                fieldWithPath("content").type(STRING).description("댓글 내용"),
                fieldWithPath("regUserName").type(STRING).description("댓글 등록자명"),
                fieldWithPath("regDate").type(STRING).description("댓글 등록일")
        };
    }

    private void givenComments() {
        given(commentQueryService.getCommentsByBoardSeq(any()))
                .willReturn(Arrays.asList(
                        CommentQueryResponse.builder()
                                .comment(CommentQueryResponse.CommentResponse.builder()
                                        .commentId(1)
                                        .content("댓글")
                                        .regUserName("사람")
                                        .regDate(LocalDateTime.now().minusMinutes(1))
                                        .build())
                                .subComments(Arrays.asList(CommentQueryResponse.CommentResponse.builder()
                                        .commentId(2)
                                        .content("대댓글")
                                        .regUserName("사람2")
                                        .regDate(LocalDateTime.now())
                                        .build()))
                                .build()));
    }
}