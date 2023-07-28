package com.example.board.doc.auth;

import com.example.board.api.auth.api.LoginApi;
import com.example.board.api.auth.query.FailedLoginException;
import com.example.board.api.auth.query.LoginResponse;
import com.example.board.api.auth.query.LoginService;
import com.example.board.api.auth.query.TokenGenerator;
import com.example.board.doc.ApiDocBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LoginApiDoc extends ApiDocBase {
    @Mock
    private LoginService loginService;
    @InjectMocks
    private LoginApi loginApi;

    @Override
    public Object getApi() {
        return loginApi;
    }

    @Override
    public boolean applyTokenFilter() {
        return false;
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
                        responseFields(apiObjectResponseFieldDescriptor())
                                .andWithPrefix("data.", fieldWithPath("token").type(STRING).description("토큰"))
                        ));
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
                        responseFields(apiObjectResponseFieldDescriptor())));
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