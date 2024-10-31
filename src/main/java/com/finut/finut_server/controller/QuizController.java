package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
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
    @GetMapping("/correct/{quizId}")
    public ApiResponse<String> quizCorrect(@PathVariable Long quizId, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Authorization 헤더에서 Access Token을 가져옵니다.
        String header = request.getHeader("Authorization");
        Users user;
        Optional<Quiz> quiz;

        if (header != null && header.startsWith("Bearer "))
//        if(true)
        {
            String accessToken = header.substring(7); // "Bearer " 제거
//            String accessToken;

            try {
                // Access Token을 이용해 사용자 정보를 조회합니다.
                Userinfoplus userInfo = googleAuthService.getUserInfo(accessToken);
//                System.out.println("User ID: " + userInfo.getId());
//                System.out.println("User Email: " + userInfo.getEmail());
                // 필요한 경우 userInfo 객체를 SecurityContext에 저장해 인증 정보를 유지할 수 있습니다.

//                System.out.println("User: " + usersService.getUserIdByEmail(userInfo.getEmail()).getId());
//                userId = usersService.getUserIdByEmail(userInfo.getEmail()).getId();
                user = usersService.getUserIdByEmail(userInfo.getEmail());
                quiz = quizService.getQuizByQuizId(quizId);

                if (quiz.isPresent()) {
                    quizDoneService.saveQuizDone(user, quiz, true); //db 생성(isCorrect = true)
                    usersService.updateDiffLevelCnt(user.getId()); //diffQuizCnt++, levelQuizCnt++
                    // 성공 응답 반환
                    return ApiResponse.onSuccess("success");
                }
                else
                    return ApiResponse.onFailure("500", "No Quiz", "data");


            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return ApiResponse.onFailure("500", "Invalid or expired access token.", "data");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ApiResponse.onFailure("500", "Authorization header missing or malformed.", "data");
        }
    }

    // 퀴즈 틀렸을 때 api/ db 생성(isCorrect = false)
    @GetMapping("/wrong/{quizId}")
    public ApiResponse<String> quizWrong(@PathVariable Long quizId, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Authorization 헤더에서 Access Token을 가져옵니다.
        String header = request.getHeader("Authorization");
        Users user;
        Optional<Quiz> quiz;

        if (header != null && header.startsWith("Bearer "))
//        if(true)
        {
            String accessToken = header.substring(7); // "Bearer " 제거
//            String accessToken;

            try {
                // Access Token을 이용해 사용자 정보를 조회합니다.
                Userinfoplus userInfo = googleAuthService.getUserInfo(accessToken);
//                System.out.println("User ID: " + userInfo.getId());
//                System.out.println("User Email: " + userInfo.getEmail());
//                System.out.println("User: " + usersService.getUserIdByEmail(userInfo.getEmail()).getId());
//                userId = usersService.getUserIdByEmail(userInfo.getEmail()).getId();
                user = usersService.getUserIdByEmail(userInfo.getEmail());
                quiz = quizService.getQuizByQuizId(quizId);

                if (quiz.isPresent()) {
                    quizDoneService.saveQuizDone(user, quiz, false); //db 생성(isCorrect = false)
                    // 성공 응답 반환
                    return ApiResponse.onSuccess("success");
                }
                else
                    return ApiResponse.onFailure("500", "No Quiz", "data");


            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return ApiResponse.onFailure("500", "Invalid or expired access token.", "data");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ApiResponse.onFailure("500", "Authorization header missing or malformed.", "data");
        }
    }

}
