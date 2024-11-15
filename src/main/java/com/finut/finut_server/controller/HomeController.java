package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.config.auth.dto.SessionUser;
import com.finut.finut_server.domain.user.UserResponseDTO;
import com.finut.finut_server.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Base64;

@Tag(name = "Home Controller", description = "홈, 로그인, 로그아웃 관련 api")
@RestController
public class HomeController {

    private final HttpSession httpSession;

    public HomeController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private UsersService userService;

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.loginUserDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "로그인 성공", description = "로그인 성공시 해당 api에 리다이렉션 되어 수행됩니다")
    @GetMapping("/success")
    @ResponseBody
    public ResponseEntity<?> getTokens(@AuthenticationPrincipal OidcUser oidcUser,
                                       @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        if (authorizedClient == null) {
            throw new IllegalStateException("Authorized client is null");
        }

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        UserResponseDTO.loginUserDTO loginUserDTO = new UserResponseDTO.loginUserDTO(user.getEmail(), accessToken);

        // 고정된 redirect URI로 리디렉션 설정
        //String redirectUri = "http://ec2-15-165-175-91.ap-northeast-2.compute.amazonaws.com:3000/main";
	String redirectUri = "http://localhost:3000/main";


        HttpHeaders headers = new HttpHeaders();
        // accessToken을 Base64로 인코딩
        String encodedAccessToken = Base64.getEncoder().encodeToString(accessToken.getBytes());
        headers.setLocation(URI.create(redirectUri + "?email=" + user.getEmail() + "&accessToken=" + encodedAccessToken));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Redirect
    }
//    @Operation(summary = "로그인 성공", description = "로그인 성공시 해당 api에 리다이렉션 되어 수행됩니다")
//    @GetMapping("/success")
//    @ResponseBody
//    public ApiResponse<UserResponseDTO.loginUserDTO> getTokens(@AuthenticationPrincipal OidcUser oidcUser,
//                                         @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
//        if (authorizedClient == null) {
//            throw new IllegalStateException("Authorized client is null");
//        }
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//
//        UserResponseDTO.loginUserDTO loginUserDTO = new UserResponseDTO.loginUserDTO(user.getEmail(), accessToken);
//        return ApiResponse.onSuccess(loginUserDTO);
//    }
}
