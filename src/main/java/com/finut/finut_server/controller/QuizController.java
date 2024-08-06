package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.converter.QuizConverter;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRequestDTO;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
import com.finut.finut_server.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@Tag(name = "Quiz Controller", description = "퀴즈 관련 api")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @Operation(summary = "퀴즈 내용 불러오기", description = "퀴즈를 보여줍니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuizResponseDTO.getQuizDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀴즈 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("")
    public ApiResponse<QuizResponseDTO.getQuizDto> getQuiz(@RequestParam(name="userId") Long userId){
        Quiz quiz = quizService.getQuiz();
        return ApiResponse.onSuccess(QuizConverter.toGetQuizDto(userId, quiz));
    }

    @Operation(summary = "퀴즈 데이터 저장", description = "생성된 퀴즈 데이터를 DB에 저장합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuizResponseDTO.saveQuizDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "올바른 날짜나 시간이 아닙니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @PostMapping("/save")
    public ApiResponse<QuizResponseDTO.saveQuizDto> saveQuiz(@RequestBody @Valid QuizRequestDTO.saveQuiz request){
        Quiz quiz = quizService.saveQuiz(request);
        return ApiResponse.onSuccess(QuizConverter.toSaveQuizDto(quiz));
    }

    @Operation(summary = "퀴즈 정답 판독 후 돈 적립", description = "사용자가 입력한 답이 정답인지 판독한 후, 정답 여부에 따라 돈을 적립합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuizResponseDTO.updateMoneyDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "정답 여부를 전달 받지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @PatchMapping("/money")
    public ApiResponse<QuizResponseDTO.updateMoneyDto> updateMoney(@RequestBody @Valid QuizRequestDTO.updateMoney request){
        int moneyAmount = quizService.updateMoney(request);
        return ApiResponse.onSuccess(QuizConverter.toUpdateMoneyDto(request.getUserId(), moneyAmount));
    }
}
