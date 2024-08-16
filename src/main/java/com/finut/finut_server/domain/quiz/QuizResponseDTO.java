package com.finut.finut_server.domain.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class QuizResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getQuizDto{
        Long userId; // 접속한 유저의 아이디
        Quiz quiz;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateMoneyDto{
        Long userId; // 접속한 유저의 아이디
        int moneyAmount; // 적립된 금액
    }
}
