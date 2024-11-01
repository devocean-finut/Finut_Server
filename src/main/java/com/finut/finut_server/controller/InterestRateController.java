package com.finut.finut_server.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.service.InterestRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "InterestRate Controller", description = "금리 데이터 api")
@RequestMapping("/interest-rate")
@RestController
public class InterestRateController {
    @Autowired
    private InterestRateService interestRateService;

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "오늘의 금리 정보", description = "오늘의 금리 정보를 가져오기 위한 api 입니다.")
    @GetMapping("/today")
    public ApiResponse<String> getInterestRatesToday() {
        return ApiResponse.onSuccess(interestRateService.getInterestRatesToady());
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ArrayNode.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "3개년 금리 정보", description = "3년의 금리 정보를 가져오기 위한 api 입니다")
    @GetMapping("/3")
    public ApiResponse<ArrayNode> getInterestRates3Y() {
        return ApiResponse.onSuccess(interestRateService.getInterestRates3Y());
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ArrayNode.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "5개년 금리 정보", description = "5년의 금리 정보를 가져오기 위한 api 입니다")
    @GetMapping("/5")
    public ApiResponse<ArrayNode> getInterestRates5Y() {
        return ApiResponse.onSuccess(interestRateService.getInterestRates5Y());
    }
}
