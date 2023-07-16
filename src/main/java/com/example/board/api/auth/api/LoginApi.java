package com.example.board.api.auth.api;

import com.example.board.api.auth.query.LoginRequest;
import com.example.board.api.auth.query.LoginResponse;
import com.example.board.api.auth.query.LoginService;
import com.example.board.api.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApi {
    private final LoginService loginService;

    @GetMapping("/login")
    public ApiResponse<LoginResponse> login(LoginRequest req) {
        return ApiResponse.ok(loginService.login(req));
    }
}
