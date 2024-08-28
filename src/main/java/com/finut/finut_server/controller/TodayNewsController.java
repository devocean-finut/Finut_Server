package com.finut.finut_server.controller;

import com.finut.finut_server.domain.news.NewsItemDTO;
import com.finut.finut_server.service.TodayNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class TodayNewsController {

    @Autowired
    private TodayNewsService todayNewsService;

    @GetMapping("/economy")
    public List<NewsItemDTO> newsEconomy() {
        try {
            return todayNewsService.economyFeed();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to fetch RSS Economy", e);
        }
    }

}
