package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.config.auth.dto.SessionUser;
import com.finut.finut_server.domain.user.UserResponseDTO;
import com.finut.finut_server.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Home Controller", description = "홈, 로그인, 로그아웃 관련 api")
@RestController
public class HomeController {

    private final HttpSession httpSession;

    public HomeController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
//    @Operation(summary = "초기 화면", description = "로그인을 위한 초기 화면을 구성합니다.")
//    @GetMapping("/")
//    public ApiResponse<UserResponseDTO.loginUserDTO> home() {
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        String accessToken = (String) httpSession.getAttribute("accessToken");
//        UserResponseDTO.loginUserDTO loginUserDTO = new UserResponseDTO.loginUserDTO(user.getEmail(), accessToken);
//        return ApiResponse.onSuccess(loginUserDTO);
//    }

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private UsersService userService;
    @GetMapping("/success")
    @ResponseBody
    public ApiResponse<UserResponseDTO.loginUserDTO> getTokens(@AuthenticationPrincipal OidcUser oidcUser,
                                         @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        if (authorizedClient == null) {
            throw new IllegalStateException("Authorized client is null");
        }

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//        String refreshToken = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : "No refresh token";

//        userService.saveRefreshToken(user.getEmail(), refreshToken);

        UserResponseDTO.loginUserDTO loginUserDTO = new UserResponseDTO.loginUserDTO(user.getEmail(), accessToken);
        return ApiResponse.onSuccess(loginUserDTO);
    }
}
