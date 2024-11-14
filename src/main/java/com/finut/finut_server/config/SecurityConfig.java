package com.finut.finut_server.config;

import com.finut.finut_server.config.auth.CustomOAuth2UserService;
import com.finut.finut_server.domain.user.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/", "/login/**", "/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService)
                                )
                                .defaultSuccessUrl("/success", true)
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/") // 임시 로그아웃 성공 URL
                )
                .csrf(AbstractHttpConfigurer::disable) // POST 요청을 위한 CSRF 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        // 폼 로그인을 완전히 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(UsersRepository userRepository, HttpSession httpSession, OAuth2AuthorizedClientService authorizedClientService) {
        CustomOAuth2UserService customOAuth2UserService = new CustomOAuth2UserService(userRepository, httpSession);
        customOAuth2UserService.setAuthorizedClientService(authorizedClientService);
        return customOAuth2UserService;
    }
}