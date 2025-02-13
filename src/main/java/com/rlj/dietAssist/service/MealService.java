package com.rlj.dietAssist.service;


import static com.rlj.dietAssist.exception.ErrorCode.AMOUNT_NOT_MATCH;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.MEAL_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;

import com.rlj.dietAssist.dto.FoodMacroDto;
import com.rlj.dietAssist.dto.MealCreateDto;
import com.rlj.dietAssist.dto.MealDto;
import com.rlj.dietAssist.dto.MealFoodDto;
import com.rlj.dietAssist.dto.MealModifiedDto;
import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.entity.diet.Meal;
import com.rlj.dietAssist.entity.diet.MealFood;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.BaseException;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.FoodRepository;
import com.rlj.dietAssist.repository.MealFoodRepository;
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

  private final BaseException baseException;
  private final MealRepository mealRepository;
  private final FoodService foodService;



  @Transactional
  public Meal createMeal(Long userId, MealCreateDto dto){
    if (dto.getFoodIds().size() != dto.getWeight().size()){
      throw new Exception(AMOUNT_NOT_MATCH);
    }

    User user = baseException.getUser(userId);

    Meal meal  = new Meal();
    meal.setName(dto.getMealName());

    for (int i = 0; i < dto.getFoodIds().size(); i++) {
      Long foodId = dto.getFoodIds().get(i);
      int weight = dto.getWeight().get(i);

      Food food = baseException.getFood(foodId);

      FoodMacroDto macroDto = foodService.getMacronutrients(foodId, weight);

      MealFood mealFood = new MealFood(meal, user, food, macroDto);

      meal.addMealFood(mealFood);
    }

    return mealRepository.save(meal);

  }

  //식단 조회
  public MealDto getMeal(Long mealId){
    Meal meal = mealRepository.findById(mealId)
        .orElseThrow(() -> new Exception(MEAL_NOT_FOUND));

    return new MealDto(meal);
  }

  //식단 수정
  @Transactional
  public MealDto updateMeal(Long userId, MealModifiedDto dto){
    User user = baseException.getUser(userId);
    Food food = baseException.getFood(dto.getFoodId());
    Meal meal = baseException.getMeal(dto.getMealId());
    MealFood mealFood = baseException.getMealFood(dto.getMealFoodId());

    FoodMacroDto macroDto = foodService.getMacronutrients(dto.getFoodId(), dto.getWeight());

    mealFood.update(food, macroDto);

    return new MealDto(meal);
  }

  //식단 삭제
  @Transactional
  public void deleteMeal(Long userId, Long mealId){

    Meal meal = baseException.getMeal(mealId);

    mealRepository.delete(meal);

  }





}
