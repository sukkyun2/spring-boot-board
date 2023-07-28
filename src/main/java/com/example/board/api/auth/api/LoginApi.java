package com.example.board.api.auth.api;

import com.example.board.api.auth.query.FailedLoginException;
import com.example.board.api.auth.query.LoginRequest;
import com.example.board.api.auth.query.LoginResponse;
import com.example.board.api.auth.query.LoginService;
import com.example.board.api.common.api.ApiResponse;
import com.example.board.api.user.query.NotExistUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApi {
    private final LoginService loginService;

    @GetMapping("/login")
    public ApiResponse<?> login(LoginRequest req) {
        try {
            return ApiResponse.ok(loginService.login(req));
        } catch (FailedLoginException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
