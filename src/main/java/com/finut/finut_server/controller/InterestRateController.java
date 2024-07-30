package com.finut.finut_server.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
import com.finut.finut_server.service.InterestRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "InterestRate Controller", description = "금리 데이터 api")
@RequestMapping("/interest-rate")
@RestController
public class InterestRateController {
    @Autowired
    private InterestRateService interestRateService;

    @Operation(summary = "오늘의 금리 정보", description = "오늘의 금리 정보를 가져오기 위한 api 입니다.")
    @GetMapping("/today")
    public ApiResponse<String> getInterestRatesToday() {
        return ApiResponse.onSuccess(interestRateService.getInterestRatesToady());
    }

    @Operation(summary = "3개년 금리 정보", description = "3년의 금리 정보를 가져오기 위한 api 입니다")
    @GetMapping("/3")
    public ApiResponse<ArrayNode> getInterestRates3Y() {
        return ApiResponse.onSuccess(interestRateService.getInterestRates3Y());
    }

    @Operation(summary = "5개년 금리 정보", description = "5년의 금리 정보를 가져오기 위한 api 입니다")
    @GetMapping("/5")
    public ApiResponse<ArrayNode> getInterestRates5Y() {
        return ApiResponse.onSuccess(interestRateService.getInterestRates5Y());
    }
}
