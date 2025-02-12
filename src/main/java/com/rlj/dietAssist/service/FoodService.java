package com.rlj.dietAssist.service;

import static com.rlj.dietAssist.exception.ErrorCode.ALREADY_EXISTING_FOOD;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.GRAM_UNIT_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rlj.dietAssist.dto.FoodDto;
import com.rlj.dietAssist.dto.FoodMacroDto;
import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.FoodRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FoodService {

  private final String API_KEY;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private final TranslationService translationService;

  private final FoodRepository foodRepository;

  private static final String BASE_URL = "https://api.nal.usda.gov/fdc/v1/";

  public FoodService( @Value("${spring.api.usda}") String key, RestTemplate restTemplate,
      ObjectMapper objectMapper, TranslationService translationService,
      FoodRepository foodRepository) {
    this.API_KEY = key;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.translationService = translationService;
    this.foodRepository = foodRepository;
  }


  //검색값 조회
  public List<FoodDto> getFoodNutrients(String foodName) {

    String englishText = translationService.translateKoreanToEnglish(foodName);

    String url = BASE_URL + "foods/search" +
        "?query=" + englishText + "&api_key=" + API_KEY;

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    try {
      JsonNode root = objectMapper.readTree(response.getBody());
      JsonNode foods = root.path("foods");

      List<FoodDto> foodList = new ArrayList<>();

      int cnt = 0;

      for (JsonNode item : foods) {

        if (cnt >= 10) break; //10개까지만 조회

        JsonNode nutrients = item.path("foodNutrients");
        FoodDto foodDto = new FoodDto();

        foodDto.setId(item.path("fdcId").asLong());
        foodDto.setFoodName(item.path("description").asText());
        foodDto.setWeight(100);
        foodDto.setEnergy(getNutrientValue(nutrients, 1008));  // Energy (kcal)
        foodDto.setCarbohydrate(getNutrientValue(nutrients, 1005)); // Carbohydrates (g)
        foodDto.setSugar(getNutrientValue(nutrients, 2000)); // Sugars (g)
        foodDto.setProtein(getNutrientValue(nutrients, 1003)); // Protein (g)
        foodDto.setTotalFat(getNutrientValue(nutrients, 1004)); // Total Fat (g)

        foodList.add(foodDto);
        cnt++;
      }
      return foodList;

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return List.of();
    }
  }

  //특정 ID 조회
  public String getFoodIdNutrient(String foodId) {

    String url = BASE_URL + "food/" + foodId + "?api_key=" + API_KEY;

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

  //검색값에 원하는 식품 저장
  @Transactional
  public void saveFoodNutrient(Long id, String name){

    List<FoodDto> foodList = getFoodNutrients(name);

    FoodDto foodDto = foodList.stream().filter(food -> Objects.equals(food.getId(), id))
        .findFirst().orElseThrow(() -> new Exception(FOOD_NOT_FOUND));

    if (foodRepository.existsByName(name)){
      throw new Exception(ALREADY_EXISTING_FOOD);
    }

    Food food = Food.builder()
        .name(foodDto.getFoodName())
        .weight(foodDto.getWeight())
        .energy(foodDto.getEnergy())
        .carbohydrate(foodDto.getCarbohydrate())
        .sugar(foodDto.getSugar())
        .protein(foodDto.getProtein())
        .totalFat(foodDto.getTotalFat())
        .build();

    foodRepository.save(food);

  }

  //그램수 단위별 값 확인
  public FoodMacroDto getMacronutrients(Long foodId, int weight){

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new Exception(FOOD_NOT_FOUND));

    //10그램 이상 1kg 이하,  10그램 단위로만 확인 가능
    if (weight < 10 || weight > 1000 || weight % 10 != 0){
      throw new Exception(GRAM_UNIT_ERROR);
    }

    FoodMacroDto macroDto = FoodMacroDto.from(food);

    macroDto.setWeight(getMacro(macroDto.getWeight(), weight));
    macroDto.setEnergy(getMacro(macroDto.getEnergy(), weight));
    macroDto.setCarbohydrate(getMacro(macroDto.getCarbohydrate(), weight));
    macroDto.setProtein(getMacro(macroDto.getProtein(), weight));
    macroDto.setTotalFat(getMacro(macroDto.getTotalFat(), weight));
    macroDto.setSugar(getMacro(macroDto.getSugar(), weight));

    return macroDto;
  }

  private float getMacro(float nutrient, int weight) {

    //값이 0 이면 0 리턴
    if (nutrient == 0){
      return 0.0f;
    }

    // 부동소수점 오차 방지를 위한 BigDecimal 사용
    BigDecimal nutrientBD = BigDecimal.valueOf(nutrient);
    BigDecimal ratio = BigDecimal.valueOf(weight)
        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

    //소수점 한자리까지 반올림한 후 float 변환
    return nutrientBD.multiply(ratio).setScale(1, RoundingMode.HALF_UP).floatValue();
  }



}



