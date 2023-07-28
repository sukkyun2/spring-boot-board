package com.example.board.doc.board;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.BoardListQueryApi;
import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardStatus;
import com.example.board.api.board.query.BoardListQueryResponse;
import com.example.board.api.board.query.BoardListQueryService;
import com.example.board.api.common.domain.Reg;
import com.example.board.api.user.domain.User;
import com.example.board.config.BoardUserTokenFilter;
import com.example.board.doc.ApiDocBase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardListQueryApiDoc extends ApiDocBase {
    @Mock
    private BoardListQueryService boardListQueryService;
    @InjectMocks
    private BoardListQueryApi boardListQueryApi;

    @Override
    public Object getApi() {
        return boardListQueryApi;
    }

    @Test
    @DisplayName("게시판 목록 조회")
    void getBoardList() throws Exception {
        givenBoardList();

        mockMvc.perform(get("/api/board?offset=0&limit=10")
                        .cookie(new Cookie("token", TokenGenerator.gen(1, "사람"))))
                .andExpect(status().isOk())
                .andDo(document("get-board-list",
                        requestCookies(
                                cookieWithName("token").description("토큰")
                        ),
                        queryParameters(
                                parameterWithName("offset").description("페이지 번호"),
                                parameterWithName("limit").description("페이지 당 개수|default 10")
                        ),
                        responseFields(apiArrayResponseFieldDescriptor())
                                .andWithPrefix("data[].",
                                        fieldWithPath("seq").type(NUMBER).description("게시판 고유 ID"),
                                        fieldWithPath("title").type(STRING).description("게시판 제목"),
                                        fieldWithPath("regUserId").type(NUMBER).description("등록자 ID"),
                                        fieldWithPath("regUserName").type(STRING).description("등록자 명"),
                                        fieldWithPath("createdAt").type(STRING).description("게시판 생성일"),
                                        fieldWithPath("viewCount").type(NUMBER).description("조회수"))
                ));
    }

    private void givenBoardList() {
        User givenUser = new User(1, "사람");
        Board givenBoard = new Board(1, "제목", "내용", Collections.emptyList(), BoardStatus.NOT_DELETED, 3, Reg.of(1), null);

        given(boardListQueryService.getBoardList(any()))
                .willReturn(Collections.singletonList(BoardListQueryResponse.of(givenBoard, givenUser)));
    }
}