package com.finut.finut_server.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class loginUserDTO {
        String email;
        String accessToken;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateAttendance {
        Long userId;
        String message;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class viewUserInfo {
        Long userId;
        String name;
        Long money;
        String picture;
        int xp;
        String levelName;
    }
}
