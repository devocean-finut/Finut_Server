package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.user.UserResponseDTO;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "사용자 관련 api")
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UsersService usersService;

    @Operation(summary = "출석 체크", description = "출석체크 수가 5의 배수면 월급을 받는 API 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.updateAttendance.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "사용자 정보를 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @PatchMapping("/attend")
    public ApiResponse<UserResponseDTO.updateAttendance> updateAttendance(HttpServletRequest request, HttpServletResponse response){
        Users user = usersService.getUserIdByToken(request, response);
        UserResponseDTO.updateAttendance updateAttendance = usersService.updateAttendDate(user.getId());
        return ApiResponse.onSuccess(updateAttendance);
    }

    @Operation(summary = "프로필 확인", description = "사용자가 본인의 정보를 확인하는 API 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.viewUserInfo.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "사용자 정보를 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/profile")
    public ApiResponse<UserResponseDTO.viewUserInfo> viewUserInfo(HttpServletRequest request, HttpServletResponse response){
        Users user = usersService.getUserIdByToken(request, response);
        UserResponseDTO.viewUserInfo viewUserInfo = usersService.viewUserInfo(user.getId());
        return ApiResponse.onSuccess(viewUserInfo);
    }
}
