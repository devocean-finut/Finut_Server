package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.config.auth.dto.SessionUser;
import com.finut.finut_server.domain.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "Home Controller", description = "홈, 로그인, 로그아웃 관련 api")
@RestController
public class HomeController {

    private final HttpSession httpSession;

    public HomeController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    @Operation(summary = "초기 화면", description = "로그인을 위한 초기 화면을 구성합니다.")
    @GetMapping("/")
    public ApiResponse<UserResponseDTO.loginUserDTO> home() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String accessToken = (String) httpSession.getAttribute("accessToken");
        UserResponseDTO.loginUserDTO loginUserDTO = new UserResponseDTO.loginUserDTO(user.getEmail(), accessToken);
        return ApiResponse.onSuccess(loginUserDTO);
    }
}
