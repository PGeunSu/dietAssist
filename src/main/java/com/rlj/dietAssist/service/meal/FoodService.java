package com.rlj.dietAssist.service.meal;

import static com.rlj.dietAssist.exception.ErrorCode.ALREADY_EXISTING_FOOD;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.GRAM_UNIT_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rlj.dietAssist.dto.food.FoodDto;
import com.rlj.dietAssist.dto.food.FoodMacroDto;
import com.rlj.dietAssist.dto.food.RegisterFood;
import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.exception.BaseException;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.meal.FoodRepository;
import com.rlj.dietAssist.service.auth.Translator;
import com.rlj.dietAssist.service.user.FavoriteService;
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
  private final Translator translator;

  private final FoodRepository foodRepository;
  private final BaseException baseException;
  private final FavoriteService favoriteService;

  private static final String BASE_URL = "https://api.nal.usda.gov/fdc/v1/";

  public FoodService( @Value("${spring.api.usda}") String key, RestTemplate restTemplate,
      ObjectMapper objectMapper, Translator translator,
      FoodRepository foodRepository, BaseException baseException, FavoriteService favoriteService) {
    this.API_KEY = key;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.translator = translator;
    this.foodRepository = foodRepository;
    this.baseException = baseException;
    this.favoriteService = favoriteService;
  }


  //검색값 조회
  public List<FoodDto> getFoodNutrients(String foodName) {

    String englishText = translator.translateKoreanToEnglish(foodName);

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

        FoodDto foodDto = FoodDto.getFoodApi(
            item.path("fdcId").asLong(),
            item.path("description").asText(),
            100, // Weight(g)
            getNutrientValue(nutrients, 1008),  // Energy (kcal)
            getNutrientValue(nutrients, 1005), // Carbohydrates (g)
            getNutrientValue(nutrients, 2000), // Sugars (g)
            getNutrientValue(nutrients, 1003),  // Protein (g)
            getNutrientValue(nutrients, 1004) // Total Fat (g)
        );

        foodList.add(foodDto);
        cnt++;
      }
      return foodList;

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return List.of();
    }
  }

  private float getNutrientValue(JsonNode nutrients, int nutrientId) {
    for (JsonNode nutrient : nutrients) {
      if (nutrient.path("nutrientId").asInt() == nutrientId) {
        return (float) nutrient.path("value").asDouble();
      }
    }
    return 0.0f; // 값이 없으면 0 반환
  }


  //직접 식품 저장
  @Transactional
  public void selfFoodNutrient(Long userId, RegisterFood registerFood){

    if (foodRepository.existsByName(registerFood.getFoodName())){
      throw new Exception(ALREADY_EXISTING_FOOD);
    }

    Food saveFood = foodRepository.save(Food.registerFood(registerFood));

    //저장 시 자동으로 즐겨찾기
    favoriteService.addFavorite(userId, saveFood.getId(), registerFood.getNotes());
  }

  //검색값에 원하는 식품 저장
  @Transactional
  public FoodDto saveFoodNutrient(Long id, String name){

    List<FoodDto> foodList = getFoodNutrients(name);

    FoodDto foodDto = foodList.stream().filter(food -> Objects.equals(food.getId(), id))
        .findFirst().orElseThrow(() -> new Exception(FOOD_NOT_FOUND));

    if (foodRepository.existsByName(name)){
      throw new Exception(ALREADY_EXISTING_FOOD);
    }

    Food food = Food.from(foodDto);

    foodRepository.save(food);

    return FoodDto.from(id, food);

  }

  //그램수 단위별 값 확인
  public FoodMacroDto getMacronutrients(Long foodId, int weight){

    Food food = baseException.getFood(foodId);

    //10그램 이상 1kg 이하,  10그램 단위로만 확인 가능
    if (weight < 10 || weight > 1000 || weight % 10 != 0){
      throw new Exception(GRAM_UNIT_ERROR);
    }

    return FoodMacroDto.from(
        food.getName(),
        getMacro(food.getWeight(), weight),
        getMacro(food.getEnergy(), weight),
        getMacro(food.getCarbohydrate(), weight),
        getMacro(food.getProtein(), weight),
        getMacro(food.getFat(), weight),
        getMacro(food.getSugar(), weight));
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



