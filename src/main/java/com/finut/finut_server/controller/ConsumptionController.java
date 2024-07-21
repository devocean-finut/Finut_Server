package com.finut.finut_server.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Consumption Controller", description = "소비 기능 관련 api")
@RequestMapping("/consumption")
@Controller
public class ConsumptionController {
    @Operation(summary = "전체 물품 보기", description = "전체 물품을 보기 위한 api 입니다")
    @GetMapping("/products")
    public String readAllProducts() {
        return "";
    }

    @Operation(summary = "물품 상세 정보 보기", description = "각 물품의 상세 정보를 보기 위한 api 입니다")
    @GetMapping("/products/{productId}")
    public String readProduct(@PathVariable Long productId) {
        return "";
    }

    @Operation(summary = "물품 구매", description = "물품을 구매하기 위한 api 입니다")
    @PutMapping("/buy")
    public String buyProduct(@RequestParam Long productId) {
        return "";
    }

    @Operation(summary = "내 물품 보기", description = "구매한 물품들을 모두 위한 api 입니다")
    @GetMapping("/myPurchases")
    public String readMyPurchases(@RequestParam Long userId) {
        return "";
    }
}
