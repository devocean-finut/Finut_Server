package com.finut.finut_server.controller;


import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.domain.product.Product;
import com.finut.finut_server.domain.purchases.Purchases;
import com.finut.finut_server.service.ConsumptionService;
import com.finut.finut_server.service.PurchasesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Consumption Controller", description = "소비 기능 관련 api")
@RequestMapping("/consumption")
@RestController
public class ConsumptionController {

    private final ConsumptionService consumptionService;
    private final PurchasesService purchasesService;

    @Autowired
    public ConsumptionController(ConsumptionService consumptionService, PurchasesService purchasesService) {
        this.consumptionService = consumptionService;
        this.purchasesService = purchasesService;
    }
    @Operation(summary = "전체 물품 보기", description = "전체 물품을 보기 위한 api 입니다")
    @GetMapping("/products")
    public ApiResponse<List<Product>> readAllProducts() {
        return ApiResponse.onSuccess(ConsumptionService.getAllProducts());
    }

    @Operation(summary = "물품 상세 정보 보기", description = "각 물품의 상세 정보를 보기 위한 api 입니다")
    @GetMapping("/products/{productId}")
    public ApiResponse<Optional<Product>> readProduct(@PathVariable Long productId) {
        return ApiResponse.onSuccess(ConsumptionService.getProductInfo(productId));
    }

    @Operation(summary = "물품 구매", description = "물품을 구매하기 위한 api 입니다")
    @GetMapping("/buy")
    public ApiResponse<Purchases> buyProduct(@RequestParam Long userId, @RequestParam Long productId) {
        return ApiResponse.onSuccess(PurchasesService.buyProduct(userId, productId));
    }

    @Operation(summary = "내 물품 보기", description = "구매한 물품들을 모두 위한 api 입니다")
    @GetMapping("/my-purchases")
    public ApiResponse<List<Product>> readMyPurchases(@RequestParam Long userId) {
        return ApiResponse.onSuccess(PurchasesService.getMyProducts(userId));
    }
}
