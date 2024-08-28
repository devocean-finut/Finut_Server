package com.finut.finut_server.service;

import com.finut.finut_server.domain.news.NewsItemDTO;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;
import com.rometools.rome.io.SyndFeedInput;

import org.jdom2.Element;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TodayNewsService {
    private static final String economy_URL = "https://www.mk.co.kr/rss/30100041/";

    public List<NewsItemDTO> economyFeed() throws Exception {
        URL feedSource = new URL(economy_URL);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));

        return feed.getEntries().stream().map(this::convertToNewsItem).collect(Collectors.toList());
    }

    private NewsItemDTO convertToNewsItem(SyndEntry entry) {
        NewsItemDTO news = new NewsItemDTO();

        //URL 에서 number 추출
        String url = entry.getLink();
        String[] parts = url.split("/");
        news.setNumber(Integer.parseInt(parts[parts.length-1]));

        // 기본 정보 설정
        news.setTitle(entry.getTitle());
        news.setLink(url.replace("www.", "m."));
        news.setDescription(entry.getDescription().getValue());


        // 이미지 추출
        String imageUrl = null;
        List<Element> foreignMarkup = entry.getForeignMarkup();  // 커스텀 태그 추출
        for (Element element : foreignMarkup) {
            System.out.println(element);
            if ("content".equals(element.getName()) && "image".equals(element.getAttributeValue("medium"))) {
                imageUrl = element.getAttributeValue("url");
                break;
            }
        }

        news.setImageUrl(imageUrl);


        return news;
    }

}
