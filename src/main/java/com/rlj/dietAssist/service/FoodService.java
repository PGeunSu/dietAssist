package com.rlj.dietAssist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rlj.dietAssist.dto.FoodDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FoodService {

  private final String SERVICE_KEY;
  private final String KEY;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  private static final String BASE_URL = "https://api.nal.usda.gov/fdc/v1/";

  public FoodService(@Value("${spring.food.api}") String apiKey,
      RestTemplate restTemplate, @Value("${spring.food.usda}") String key,
      ObjectMapper objectMapper) {
    this.SERVICE_KEY = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
    KEY = key;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }



  //검색값 조회
  public List<FoodDto> getUsdaFood(String foodName){

    String englishText = new TranslationService().translateKoreanToEnglish(foodName);

    String url = BASE_URL + "foods/search" +
        "?query=" + englishText + "&api_key=" + KEY;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    try{
      JsonNode root = objectMapper.readTree(response.getBody());
      JsonNode foods = root.path("foods");

      List<FoodDto> foodList = new ArrayList<>();
      for (JsonNode item : foods){
        JsonNode nutrients = item.path("foodNutrients");
        FoodDto foodDto = new FoodDto();

        foodDto.setId(item.path("fdcId").asLong());
        foodDto.setFoodName(item.path("description").asText());
        foodDto.setEnergy(getNutrientValue(nutrients, 1008));  // Energy (kcal)
        foodDto.setCarbohydrate(getNutrientValue(nutrients, 1005)); // Carbohydrates (g)
        foodDto.setSugar(getNutrientValue(nutrients, 2000)); // Sugars (g)
        foodDto.setProtein(getNutrientValue(nutrients, 1003)); // Protein (g)
        foodDto.setFat(getNutrientValue(nutrients, 1004)); // Total Fat (g)
        foodDto.setSaturatedFat(getNutrientValue(nutrients, 1258)); // Saturated Fat (g)
        foodDto.setUnSaturatedFat(getNutrientValue(nutrients, 1292)); // Unsaturated Fat (g)

        foodList.add(foodDto);
      }
      return foodList;

    }catch (Exception e){
      e.printStackTrace();
      return List.of();
    }
  }

  //특정 ID 조회
  public String getUsdaFoodId(String foodId){

    String url = BASE_URL + "food/" + foodId + "?api_key=" + KEY;

    return restTemplate.getForEntity(url, String.class).getBody();

  }


  private float getNutrientValue(JsonNode nutrients, int nutrientId) {
    for (JsonNode nutrient : nutrients) {
      if (nutrient.path("nutrientId").asInt() == nutrientId) {
        return (float) nutrient.path("value").asDouble();
      }
    }
    return 0.0f; // 값이 없으면 0 반환
  }


  public List<FoodDto> getFoodNutrient(String foodName) throws URISyntaxException {
    String url = "https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo01/getFoodNtrCpntDbInq01"
        + "?serviceKey=" + SERVICE_KEY
        + "&numOfRows=10"
        + "&pageNo=1"
        + "&type=json"
        + "&FOOD_NM_KR=" + URLEncoder.encode(foodName, StandardCharsets.UTF_8);

    URI uri = new URI(url);

    // 요청 엔터티 생성
    HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity,
        String.class);

    // 받은 JSON 문자열을 ObjectMapper로 파싱
    ObjectMapper objectMapper = new ObjectMapper();
    List<FoodDto> foodDtoList = new ArrayList<>();

    try {
      JsonNode responseBody = objectMapper.readTree(response.getBody());
      JsonNode items = responseBody.path("body").path("items");

      if (items.isArray() && !items.isEmpty()) {
        for (JsonNode item : items) {
          FoodDto foodDto = new FoodDto();
          foodDto.setId(item.path("NUM").asLong());
          foodDto.setFoodName(item.path("FOOD_NM_KR").asText());
          foodDto.setEnergy(getFloatValue(item, "AMT_NUM1"));
          foodDto.setCarbohydrate(getFloatValue(item, "AMT_NUM7"));
          foodDto.setSugar(getFloatValue(item, "AMT_NUM8"));
          foodDto.setProtein(getFloatValue(item, "AMT_NUM3"));
          foodDto.setFat(getFloatValue(item, "AMT_NUM4"));
          foodDto.setSaturatedFat(getFloatValue(item, "AMT_NUM25"));
          foodDto.setUnSaturatedFat(getFloatValue(item, "AMT_NUM62"));

          foodDtoList.add(foodDto);
        }
      }else {
        System.out.println("No items found in the response.");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return foodDtoList;
  }

  private float getFloatValue(JsonNode node, String fieldName) {
    JsonNode fieldNode = node.path(fieldName);
    if (fieldNode.isMissingNode()) {
      return 0.0f;  // 기본값
    }
    try {
      return fieldNode.asText().isEmpty() ? 0.0f : Float.parseFloat(fieldNode.asText());
    } catch (NumberFormatException e) {
      return 0.0f;  // 숫자 형식 오류 처리
    }
  }

}

