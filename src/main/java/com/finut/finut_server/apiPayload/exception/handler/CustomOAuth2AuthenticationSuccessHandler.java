package com.finut.finut_server.apiPayload.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService authorizedClientService;

    public CustomOAuth2AuthenticationSuccessHandler(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            String principalName = oauthToken.getName();

            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);
            String accessToken = authorizedClient.getAccessToken().getTokenValue();

            // 세션에 accessToken 저장
            request.getSession().setAttribute("accessToken", accessToken);

            System.out.println("Access Token: " + accessToken); // 디버그를 위한 출력
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
