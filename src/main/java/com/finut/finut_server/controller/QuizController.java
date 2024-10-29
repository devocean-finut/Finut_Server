package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
import com.finut.finut_server.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
@Tag(name = "Quiz Controller", description = "퀴즈 관련 api")
public class QuizController {
    @Autowired
    private QuizService quizService;
    private QuizResponseDTO quizResponseDTO;

    @Operation(summary = "랜덤으로 퀴즈 내용 불러오기", description = "퀴즈를 보여줍니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuizResponseDTO.randomQuizResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀴즈 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("")
    public ApiResponse<Optional<Quiz>> getQuiz(@RequestParam(name="userId") Long userId){
        Optional<Quiz> quiz = quizService.getQuiz(userId);
        if(quiz.isPresent())
            return ApiResponse.onSuccess(quiz);
        return ApiResponse.onFailure("400", "퀴즈 내용을 제대로 가져오지 못했습니다", quiz);
    }


    // 퀴즈 맞췄을 때 api/ db 생성(isCorrect = true), diffQuizCnt++, levelQuizCnt++
    public ApiResponse<String> quizCorrect() {


        return ApiResponse.onSuccess("success");
    }
    // 퀴즈 틀렸을 때 api/ db 생성(isCorrect = false)

}
