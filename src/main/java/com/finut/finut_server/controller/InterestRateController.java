package com.finut.finut_server.controller;

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
    public String getInterestRatesToday() {
        return interestRateService.getInterestRatesToady();
    }

    @Operation(summary = "3개년 금리 정보", description = "3년의 금리 정보를 가져오기 위한 api 입니다")
    @GetMapping("/3")
    public String getInterestRates3Y() {
        return interestRateService.getInterestRates3Y();
    }

    @Operation(summary = "5개년 금리 정보", description = "5년의 금리 정보를 가져오기 위한 api 입니다")
    @GetMapping("/5")
    public String getInterestRates5Y() {
        return interestRateService.getInterestRates5Y();
    }
}
