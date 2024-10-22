package com.finut.finut_server.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GEUST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}