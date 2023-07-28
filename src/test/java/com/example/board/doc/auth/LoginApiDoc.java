package com.example.board.doc.auth;

import com.example.board.api.auth.api.LoginApi;
import com.example.board.api.auth.query.FailedLoginException;
import com.example.board.api.auth.query.LoginResponse;
import com.example.board.api.auth.query.LoginService;
import com.example.board.api.auth.query.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class LoginApiDoc {

    private MockMvc mockMvc;
    @Mock
    private LoginService loginService;
    @InjectMocks
    private LoginApi loginApi;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginApi)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        givenLoginSuccess();

        mockMvc.perform(get("/login")
                        .queryParam("loginId","login121")
                        .queryParam("password","111"))
                .andExpect(status().isOk())
                .andDo(document("login",
                        queryParameters(
                                parameterWithName("loginId").description("로그인 아이디"),
                                parameterWithName("password").description("패스워드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(STRING).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메시지"),
                                fieldWithPath("data").type(OBJECT).description("데이터"),
                                fieldWithPath("data.token").type(STRING).description("토큰")
                        )));
    }

    @Test
    @DisplayName("로그인 실패")
    void given_incorrect_login_id_or_password_then_login_fail() throws Exception {
        givenLoginFail();

        mockMvc.perform(get("/login")
                        .queryParam("loginId","login121")
                        .queryParam("password","22"))
                .andExpect(status().isOk())
                .andDo(document("login-fail",
                        queryParameters(
                                parameterWithName("loginId").description("로그인 아이디"),
                                parameterWithName("password").description("패스워드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(STRING).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메시지"),
                                fieldWithPath("data").type(NULL).description("데이터")
                        )));
    }

    private void givenLoginSuccess() {
        given(loginService.login(any()))
                .willReturn(new LoginResponse(TokenGenerator.gen(1,"사람")));
    }

    private void givenLoginFail() {
        given(loginService.login(any()))
                .willThrow(new FailedLoginException());
    }

}