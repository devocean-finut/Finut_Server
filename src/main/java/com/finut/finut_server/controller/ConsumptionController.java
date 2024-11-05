package com.finut.finut_server.controller;


import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.product.Product;
import com.finut.finut_server.domain.purchases.Purchases;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.service.ConsumptionService;
import com.finut.finut_server.service.PurchasesService;
import com.finut.finut_server.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private UsersService usersService;

    @Autowired
    public ConsumptionController(ConsumptionService consumptionService, PurchasesService purchasesService) {
        this.consumptionService = consumptionService;
        this.purchasesService = purchasesService;
    }
    @Operation(summary = "전체 물품 보기", description = "전체 물품을 보기 위한 api 입니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/products")
    public ApiResponse<List<Product>> readAllProducts() {
        return ApiResponse.onSuccess(ConsumptionService.getAllProducts());
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "물품 상세 정보 보기", description = "각 물품의 상세 정보를 보기 위한 api 입니다")
    @GetMapping("/products/{productId}")
    public ApiResponse<Optional<Product>> readProduct(@PathVariable Long productId) {
        return ApiResponse.onSuccess(ConsumptionService.getProductInfo(productId));
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Purchases.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "물품 구매", description = "물품을 구매하기 위한 api 입니다")
    @GetMapping("/buy")
    public ApiResponse<Purchases> buyProduct(@RequestParam Long productId, HttpServletRequest request, HttpServletResponse response) {
        Users user = usersService.getUserIdByToken(request, response);
        return ApiResponse.onSuccess(PurchasesService.buyProduct(user.getId(), productId));
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @Operation(summary = "내 물품 보기", description = "구매한 물품들을 모두 위한 api 입니다")
    @GetMapping("/my-purchases")
    public ApiResponse<List<Product>> readMyPurchases(HttpServletRequest request, HttpServletResponse response) {
        Users user = usersService.getUserIdByToken(request, response);
        return ApiResponse.onSuccess(PurchasesService.getMyProducts(user.getId()));
    }
}
