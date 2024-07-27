package com.finut.finut_server.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class InterestRateService {
    @Value("${bank.api.base-url}")
    private String baseUrl;

    @Value("${bank.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    LocalDate today = LocalDate.now();

    private int yearValue = today.getYear();
    private int monthValue = today.getMonthValue();

    String startDate;
    final String endDate = Integer.toString(yearValue) + String.format("%02d", monthValue);

    public InterestRateService () {
        this.restTemplate = new RestTemplate();
    }

    public String getInterestRatesToady() {
        if(monthValue == 1) {
            startDate = Integer.toString(yearValue - 1) + "12";
        }
        else
        {
            startDate = Integer.toString(yearValue) + "01";
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("StatisticSearch")
                .pathSegment(apiKey)
                .pathSegment("JSON")
                .pathSegment("kr")
                .pathSegment("1") // 요청시작건수
                .pathSegment("12") // 요청종료건수
                .pathSegment("722Y001") // 통계표코드
                .pathSegment("M") // 주기
                .pathSegment(startDate) // 검색시작일자
                .pathSegment(endDate) // 검색종료일자
                .pathSegment("0101000") // 통계항목코드1
                .pathSegment("?") // 통계항목코드2
                .pathSegment("?") // 통계항목코드3
                .pathSegment("?") // 통계항목코드4
                .build()
                .toUri();

        String response = restTemplate.getForObject(uri, String.class);

        return extractLastDataValue(response);
    }

    private String extractLastDataValue(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);

            JsonNode rows = rootNode.path("StatisticSearch").path("row");

            String dataValue;
            if (rows.isArray() && rows.size() > 0) {
                JsonNode lastRow = rows.get(rows.size() - 1);
                dataValue = lastRow.path("DATA_VALUE").asText();
            } else {
                dataValue = "No data available";
            }

            ObjectNode resultNode = mapper.createObjectNode();
            resultNode.put("result", dataValue);

            return resultNode.toString();
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode errorNode = new ObjectMapper().createObjectNode();
            errorNode.put("result", "Error processing data");
            return errorNode.toString();
        }
    }

    public String getInterestRates3Y() {
        startDate = Integer.toString(yearValue - 3) + String.format("%02d", monthValue);

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("StatisticSearch")
                .pathSegment(apiKey)
                .pathSegment("JSON")
                .pathSegment("kr")
                .pathSegment("1") // 요청시작건수
                .pathSegment("36") // 요청종료건수
                .pathSegment("722Y001") // 통계표코드
                .pathSegment("M") // 주기
                .pathSegment(startDate) // 검색시작일자
                .pathSegment(endDate) // 검색종료일자
                .pathSegment("0101000") // 통계항목코드1
                .pathSegment("?") // 통계항목코드2
                .pathSegment("?") // 통계항목코드3
                .pathSegment("?") // 통계항목코드4
                .build()
                .toUri();

        String response = restTemplate.getForObject(uri, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode rowNode = rootNode.path("StatisticSearch").path("row");

            ArrayNode resultArray = objectMapper.createArrayNode();

            if (rowNode.isArray()) {
                for (JsonNode node : rowNode) {
                    String time = node.path("TIME").asText();
                    String dataValue = node.path("DATA_VALUE").asText();

                    ObjectNode newNode = objectMapper.createObjectNode();
                    newNode.put("TIME", time);
                    newNode.put("DATA_VALUE", dataValue);

                    resultArray.add(newNode);
                }
            }

            ObjectNode resultNode = objectMapper.createObjectNode();
            resultNode.set("results", resultArray);

            return objectMapper.writeValueAsString(resultNode);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getInterestRates5Y() {
        startDate = Integer.toString(yearValue - 5) + String.format("%02d", monthValue);

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("StatisticSearch")
                .pathSegment(apiKey)
                .pathSegment("JSON")
                .pathSegment("kr")
                .pathSegment("1") // 요청시작건수
                .pathSegment("60") // 요청종료건수
                .pathSegment("722Y001") // 통계표코드
                .pathSegment("M") // 주기
                .pathSegment(startDate) // 검색시작일자
                .pathSegment(endDate) // 검색종료일자
                .pathSegment("0101000") // 통계항목코드1
                .pathSegment("?") // 통계항목코드2
                .pathSegment("?") // 통계항목코드3
                .pathSegment("?") // 통계항목코드4
                .build()
                .toUri();

        String response = restTemplate.getForObject(uri, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode rowNode = rootNode.path("StatisticSearch").path("row");

            ArrayNode resultArray = objectMapper.createArrayNode();

            if (rowNode.isArray()) {
                for (JsonNode node : rowNode) {
                    String time = node.path("TIME").asText();
                    String dataValue = node.path("DATA_VALUE").asText();

                    ObjectNode newNode = objectMapper.createObjectNode();
                    newNode.put("TIME", time);
                    newNode.put("DATA_VALUE", dataValue);

                    resultArray.add(newNode);
                }
            }

            ObjectNode resultNode = objectMapper.createObjectNode();
            resultNode.set("results", resultArray);

            return objectMapper.writeValueAsString(resultNode);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
