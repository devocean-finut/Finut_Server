package com.finut.finut_server.domain.news;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NewsItemDTO {
    private String title;
    private String link;
    private String description;
    private int number;
    private String ImageUrl;
}
