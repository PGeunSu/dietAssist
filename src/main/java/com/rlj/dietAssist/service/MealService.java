package com.rlj.dietAssist.service;


import static com.rlj.dietAssist.exception.ErrorCode.AMOUNT_NOT_MATCH;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;

import com.rlj.dietAssist.dto.FoodMacroDto;
import com.rlj.dietAssist.dto.MealFoodDto;
import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.entity.diet.Meal;
import com.rlj.dietAssist.entity.diet.MealFood;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.FoodRepository;
import com.rlj.dietAssist.repository.MealRepository;
import com.rlj.dietAssist.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService {

  private final FoodRepository foodRepository;
  private final MealRepository mealRepository;
  private final FoodService foodService;
  private final UserRepository userRepository;


  @Transactional
  public Meal createMeal(Long userId, List<Long> foodIds, List<Integer> weights, String mealName){
    if (foodIds.size() != weights.size()){
      throw new Exception(AMOUNT_NOT_MATCH);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    Meal meal  = new Meal();
    meal.setName(mealName);

    for (int i = 0; i < foodIds.size(); i++) {
      Long foodId = foodIds.get(i);
      int weight = weights.get(i);

      Food food = foodRepository.findById(foodId)
          .orElseThrow(() -> new Exception(FOOD_NOT_FOUND));


      FoodMacroDto macroDto = foodService.getMacronutrients(foodId, weight);

      MealFood mealFood = new MealFood(meal, user, food, macroDto);

      meal.addMealFood(mealFood);
    }


    return mealRepository.save(meal);

  }

  public List<MealFoodDto> getMeal(Long mealId){
    Meal meal = mealRepository.findById(mealId)
        .orElseThrow(() -> new Exception(FOOD_NOT_FOUND));

    return meal.getMealFoods().stream().map(MealFoodDto::new).collect(Collectors.toList());
  }


}
