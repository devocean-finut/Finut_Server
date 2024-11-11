package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.quest.Quest;
import com.finut.finut_server.domain.quest.QuestDTO;
import com.finut.finut_server.domain.questDone.QuestDone;
import com.finut.finut_server.domain.questQuiz.QuestQuiz;
import com.finut.finut_server.domain.questQuiz.QuestQuizRepository;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import com.finut.finut_server.service.LevelService;
import com.finut.finut_server.service.QuestQuizService;
import com.finut.finut_server.service.QuestService;
import com.finut.finut_server.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quest")
@Tag(name = "Quest Controller", description = "퀘스트 관련 api")
public class QuestController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private QuestQuizService questQuizService;

    @Autowired
    private QuestService questService;

    @Autowired
    private LevelService levelService;

    @Operation(summary = "전체 퀘스트 가져오기", description = "전체 퀘스트를 가져옵니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Quest.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀘스트 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("")
    public ApiResponse<List<QuestDTO>> getQuests(){
        List<QuestDTO> quests = questService.getAllQuests();
        return ApiResponse.onSuccess(quests);
    }


    @Operation(summary = "해당 퀘스트 퀴즈들 가져오기", description = "해당 퀘스트의 퀴즈을 모두 가져옵니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuestQuiz.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "퀘스트 퀴즈 내용을 제대로 가지고오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/{questId}")
    public ApiResponse<List<QuestQuiz>> getQuestQuizes(@PathVariable Long questId){
        List<QuestQuiz> questQuizzes = questQuizService.getQuestQuizzes(questId);
        return ApiResponse.onSuccess(questQuizzes);
    }



    @Operation(summary = "퀘스트 성공", description = "퀘스트 성공시 Level을 다음 레벨으로 바꿉니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuestDone.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/done-quest/{questId}")
    public ApiResponse<Users> updateQuestDone(@PathVariable Long questId, HttpServletRequest request, HttpServletResponse response) {
        Users user = usersService.getUserIdByToken(request, response);
        questService.updateQuestDone(user, questId);
        user = levelService.upgradeUserLevel(user.getId());
        return ApiResponse.onSuccess(user);
    }


}
