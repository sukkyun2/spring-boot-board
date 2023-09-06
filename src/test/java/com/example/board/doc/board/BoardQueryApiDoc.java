package com.example.board.doc.board;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.BoardQueryApi;
import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardStatus;
import com.example.board.api.board.domain.like.LikeSummary;
import com.example.board.api.board.query.BoardQueryResponse;
import com.example.board.api.board.query.BoardQueryService;
import com.example.board.api.common.domain.Reg;
import com.example.board.api.user.domain.User;
import com.example.board.doc.ApiDocBase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BoardQueryApiDoc extends ApiDocBase {
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private BoardQueryService boardQueryService;
    @InjectMocks
    private BoardQueryApi boardQueryApi;

    @Override
    public Object getApi() {
        return boardQueryApi;
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void getBoard() throws Exception {
        givenBoard();

        mockMvc.perform(get("/api/board/{boardSeq}", 1)
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("get-board",
                        requestCookies(cookieWithName("token").description("토큰")),
                        pathParameters(parameterWithName("boardSeq").description("게시글 고유번호")),
                        responseFields(apiObjectResponseFieldDescriptor())
                                .andWithPrefix("data.",
                                        fieldWithPath("seq").type(NUMBER).description("게시판 고유번호"),
                                        fieldWithPath("title").type(STRING).description("게시판 제목"),
                                        fieldWithPath("content").type(STRING).description("게시판 내용"),
                                        fieldWithPath("viewCount").type(NUMBER).description("게시판 조회수"),
                                        fieldWithPath("likeCount").type(NUMBER).description("좋아요 개수"),
                                        fieldWithPath("unlikeCount").type(NUMBER).description("싫어요 개수"),
                                        fieldWithPath("regId").type(NUMBER).description("등록자 ID"),
                                        fieldWithPath("regDate").type(STRING).description("게시글 등록일"),
                                        fieldWithPath("regUserName").type(STRING).description("등록자 명"),
                                        fieldWithPath("updId").optional().type(NUMBER).description("수정자 ID"),
                                        fieldWithPath("updDate").optional().type(NUMBER).description("게시글 수정일"),
                                        fieldWithPath("updUserName").optional().type(STRING).description("수정자 명"))
                ));
    }

    private void givenBoard() {
        User givenUser = new User(1, "사람");
        Board givenBoard = new Board(1, "제목", "내용", Collections.emptyList(), BoardStatus.NOT_DELETED, 3, Reg.of(1), null);
        LikeSummary givenLikeSummary = new LikeSummary(1, 2);

        given(boardQueryService.getBoard(any()))
                .willReturn(BoardQueryResponse.of(givenBoard, givenUser, Optional.empty(), givenLikeSummary));
    }
}