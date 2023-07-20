package com.example.board.doc.board.api;

import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.api.board.api.BoardListQueryApi;
import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardStatus;
import com.example.board.api.board.query.BoardListQueryRequest;
import com.example.board.api.board.query.BoardListQueryResponse;
import com.example.board.api.board.query.BoardListQueryService;
import com.example.board.api.common.domain.Reg;
import com.example.board.api.user.domain.User;
import com.example.board.config.BoardUserTokenFilter;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class BoardListQueryApiDoc {
    private MockMvc mockMvc;
    @Mock
    private BoardListQueryService boardListQueryService;
    @InjectMocks
    private BoardListQueryApi boardListQueryApi;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(boardListQueryApi)
                .addFilter(new BoardUserTokenFilter())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withResponseDefaults(prettyPrint()))
                .build();
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
                        responseFields(
                                fieldWithPath("code").type(STRING).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메시지"),
                                fieldWithPath("data").type(ARRAY).description("데이터"),
                                fieldWithPath("data[].seq").type(NUMBER).description("게시판 고유 ID"),
                                fieldWithPath("data[].title").type(STRING).description("게시판 제목"),
                                fieldWithPath("data[].regUserId").type(NUMBER).description("등록자 ID"),
                                fieldWithPath("data[].regUserName").type(STRING).description("등록자 명"),
                                fieldWithPath("data[].createdAt").type(STRING).description("게시판 생성일"),
                                fieldWithPath("data[].viewCount").type(NUMBER).description("조회수")
                        )));
    }

    private void givenBoardList() {
        User givenUser = new User(1, "사람");
        Board givenBoard = new Board(1, "제목", "내용", Collections.emptyList(), BoardStatus.NOT_DELETED, 3, Reg.of(1), null);

        given(boardListQueryService.getBoardList(any()))
                .willReturn(Collections.singletonList(BoardListQueryResponse.of(givenBoard, givenUser)));
    }
}