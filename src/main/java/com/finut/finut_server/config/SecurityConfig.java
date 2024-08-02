package com.finut.finut_server.config;


import com.finut.finut_server.apiPayload.exception.handler.CustomOAuth2AuthenticationSuccessHandler;
import com.finut.finut_server.config.auth.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOAuth2AuthenticationSuccessHandler = customOAuth2AuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers("/", "/login/**", "/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/oauth2/authorization/naver")
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService)
                                )
                                .defaultSuccessUrl("/", true)
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/oauth2/authorization/naver")
                                .defaultSuccessUrl("/", true)
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .successHandler(customOAuth2AuthenticationSuccessHandler)
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/login")
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
