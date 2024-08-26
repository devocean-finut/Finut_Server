package com.finut.finut_server.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequestDTO {
    @Getter
    public static class checkAttendance {
        @NotNull
        Long userId; // 접속한 유저의 아이디
    }
}
