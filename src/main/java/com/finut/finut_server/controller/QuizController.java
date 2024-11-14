package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
import com.finut.finut_server.domain.user.UserResponseDTO;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.service.GoogleAuthService;
import com.finut.finut_server.service.QuizDoneService;
import com.finut.finut_server.service.QuizService;
import com.finut.finut_server.service.UsersService;
import com.google.api.services.oauth2.model.Userinfoplus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
@Tag(name = "Quiz Controller", description = "퀴즈 관련 api")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private final GoogleAuthService googleAuthService;

    @Autowired
    private final UsersService usersService;

    @Autowired
    private final QuizDoneService quizDoneService;
    private QuizResponseDTO quizResponseDTO;

    @Autowired
    public QuizController(GoogleAuthService googleAuthService, UsersService usersService, QuizDoneService quizDoneService) {
        this.googleAuthService = googleAuthService;
        this.usersService = usersService;
        this.quizDoneService = quizDoneService;
    }

    @Operation(summary = "랜덤으로 퀴즈 내용 불러오기", description = "유저가 풀지 않았던 문제 중에서 퀴즈를 랜덤으로 하나 가져옵니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Quiz.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀴즈 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("")
    public ApiResponse<Optional<Quiz>> getQuiz(HttpServletRequest request, HttpServletResponse response){
        Users user = usersService.getUserIdByToken(request, response);
        Optional<Quiz> quiz = quizService.getQuiz(user.getId());
        if(quiz.isPresent())
            return ApiResponse.onSuccess(quiz);
        return ApiResponse.onFailure("400", "퀴즈 내용을 제대로 가져오지 못했습니다", quiz);
    }



    @Operation(summary = "퀴즈를 맞췄을 때", description = "퀴즈를 맞췄을 때 QuizDone DB에 해당 내용을 저장하고, 난이도 상승에 필요한 퀴즈 개수와 레벨업에 필요한 XP를 증가시킵니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.checkUserXP.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀴즈 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    // 퀴즈 맞췄을 때 api/ db 생성(isCorrect = true), diffQuizCnt++, xp + 25
    @GetMapping("/correct/{quizId}")
    public ApiResponse<UserResponseDTO.checkUserXP> quizCorrect(@PathVariable Long quizId, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Users user = usersService.getUserIdByToken(request, response);

        UserResponseDTO.checkUserXP checkUserXP = null;

        if (user == null) {
            return ApiResponse.onFailure("401", "User not authenticated", checkUserXP);
        }

        Optional<Quiz> quiz = quizService.getQuizByQuizId(quizId);

        if (quiz.isPresent()) {
            quizDoneService.saveQuizDone(user, quiz.get(), true); //db 생성(isCorrect = true)
            checkUserXP = usersService.updateDiffLevelCnt(user.getId()); //diffQuizCnt++, xp + 25
            // 성공 응답 반환
            return ApiResponse.onSuccess(checkUserXP);
        }
        else {
            return ApiResponse.onFailure("500", "No Quiz", checkUserXP);
        }
    }


    // 퀴즈 틀렸을 때 api/ db 생성(isCorrect = false)
    @Operation(summary = "퀴즈를 틀렸을 때", description = "퀴즈를 틀렸을 때, 틀렸다는 정보를 저장합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀴즈 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/wrong/{quizId}")
    public ApiResponse<String> quizWrong(@PathVariable Long quizId, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Users user = usersService.getUserIdByToken(request, response);
        Optional<Quiz> quiz = quizService.getQuizByQuizId(quizId);

        if (quiz.isPresent()) {
            quizDoneService.saveQuizDone(user, quiz.get(), false); //db 생성(isCorrect = false)
            // 성공 응답 반환
            return ApiResponse.onSuccess("success");
        } else {
            return ApiResponse.onFailure("500", "No Quiz", "data");
        }
    }

    @Operation(summary = "레벨 테스트 조회", description = "레벨 테스트를 위한 퀴즈 조회 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Quiz.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀴즈 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/level")
    public ApiResponse<List<Quiz>> getQuizToLevelTest(HttpServletRequest request, HttpServletResponse response){
        Users user = usersService.getUserIdByToken(request, response);
        List<Quiz> quiz = quizService.getQuizToLevelTest(user.getId());
        if(!quiz.isEmpty())
            return ApiResponse.onSuccess(quiz);
        return ApiResponse.onFailure("400", "퀴즈 내용을 제대로 가져오지 못했습니다", quiz);
    }

}