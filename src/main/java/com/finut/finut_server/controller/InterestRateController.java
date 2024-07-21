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

    @Operation(summary = "금리 정보", description = "한국은행 Open API를 사용하여 금리 정보를 가져오기 위한 위한 api 입니다")
    @GetMapping("")
    public String getInterestRates() {
        System.out.println(interestRateService.getInterestRates());
        return interestRateService.getInterestRates();
    }
}
