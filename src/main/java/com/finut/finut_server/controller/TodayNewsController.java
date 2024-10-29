package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.code.ErrorReasonDTO;
import com.finut.finut_server.domain.news.NewsItemDTO;
import com.finut.finut_server.service.TodayNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class TodayNewsController {

    @Autowired
    private TodayNewsService todayNewsService;

    @Operation(summary = "오늘의 뉴스 - 경제", description = "오늘의 뉴스 중 경제 부분의 뉴스들을 보여줍니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "뉴스 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/economy")
    public List<NewsItemDTO> newsEconomy() {
        try {
            return todayNewsService.getNews(1); // economy
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to fetch RSS Economy", e);
        }
    }

    @Operation(summary = "오늘의 뉴스 - 부동산", description = "오늘의 뉴스 중 부동산 부분의 뉴스들을 보여줍니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "뉴스 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/realestate")
    public List<NewsItemDTO> newsRealEstate() {
        try {
            return todayNewsService.getNews(2); // real estate
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to fetch RSS Economy", e);
        }
    }

    @Operation(summary = "오늘의 뉴스 - 증권", description = "오늘의 뉴스 중 증권 부분의 뉴스들을 보여줍니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "뉴스 내용을 제대로 가지고 오지 못했습니다.",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러, 관리자에게 문의 바랍니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorReasonDTO.class)))
    })
    @GetMapping("/stock")
    public List<NewsItemDTO> newsStock() {
        try {
            return todayNewsService.getNews(3); // stock
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to fetch RSS Economy", e);
        }
    }

}
