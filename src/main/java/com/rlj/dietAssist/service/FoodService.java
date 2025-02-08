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
  private final TranslationService translationService;

  private static final String BASE_URL = "https://api.nal.usda.gov/fdc/v1/";

  public FoodService(@Value("${spring.api.food}") String apiKey,
      RestTemplate restTemplate, @Value("${spring.api.usda}") String key,
      ObjectMapper objectMapper, TranslationService translationService) {
    this.SERVICE_KEY = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
    KEY = key;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.translationService = translationService;
  }


  //검색값 조회
  public List<FoodDto> getUsdaFood(String foodName) {

    String englishText = translationService.translateKoreanToEnglish(foodName);

    String url = BASE_URL + "foods/search" +
        "?query=" + englishText + "&api_key=" + KEY;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    try {
      JsonNode root = objectMapper.readTree(response.getBody());
      JsonNode foods = root.path("foods");

      List<FoodDto> foodList = new ArrayList<>();
      for (JsonNode item : foods) {
        JsonNode nutrients = item.path("foodNutrients");
        FoodDto foodDto = new FoodDto();

        foodDto.setId(item.path("fdcId").asLong());
        foodDto.setFoodName(item.path("description").asText());
        foodDto.setWeight(100);
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

    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }

  //특정 ID 조회
  public String getUsdaFoodId(String foodId) {

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
}