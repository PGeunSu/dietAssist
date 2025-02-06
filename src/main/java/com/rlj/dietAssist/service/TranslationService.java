package com.rlj.dietAssist.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TranslationService {

  private final String CLIENT_ID;
  private final String CLIENT_KEY;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;


  public TranslationService(
      @Value("${spring.api.transId}") String clientId, @Value("${spring.api.transKey}") String clientKey,
      RestTemplate restTemplate, ObjectMapper objectMapper) {
    this.CLIENT_ID = clientId;
    this.CLIENT_KEY = clientKey;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }


  public String translateKoreanToEnglish(String text) {

    String url = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

    // HTTP 요청 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
    headers.set("X-NCP-APIGW-API-KEY", CLIENT_KEY);

    // 요청 바디 생성
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("source", "ko");
    requestBody.put("target", "en");
    requestBody.put("text", text);

    HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

    // API 요청
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity,
        String.class);

    try {
      JsonNode jsonNode = objectMapper.readTree(response.getBody());
      return jsonNode.path("message").path("result").path("translatedText").asText();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

}
