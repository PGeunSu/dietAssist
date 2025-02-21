package com.rlj.dietAssist.exception;

import static com.rlj.dietAssist.exception.ErrorCode.DAILY_DIET_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.DAILY_MEAL_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.FAVORITE_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.MEAL_FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.MEAL_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.WEIGHT_RECORD_NOT_FOUND;

import com.rlj.dietAssist.entity.diet.DailyDiet;
import com.rlj.dietAssist.entity.diet.DailyMeal;
import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.entity.diet.Meal;
import com.rlj.dietAssist.entity.diet.MealFood;
import com.rlj.dietAssist.entity.favorite.Favorite;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.entity.user.WeightRecord;
import com.rlj.dietAssist.repository.WeightRecordRepository;
import com.rlj.dietAssist.repository.DailyDietRepository;
import com.rlj.dietAssist.repository.DailyMealRepository;
import com.rlj.dietAssist.repository.FavoriteRepository;
import com.rlj.dietAssist.repository.FoodRepository;
import com.rlj.dietAssist.repository.MealFoodRepository;
import com.rlj.dietAssist.repository.MealRepository;
import com.rlj.dietAssist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaseException {

  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final FavoriteRepository favoriteRepository;
  private final MealRepository mealRepository;
  private final MealFoodRepository mealFoodRepository;
  private final DailyMealRepository dailyMealRepository;
  private final DailyDietRepository dailyDietRepository;
  private final WeightRecordRepository weightRecordRepository;

  public void validateUserExists(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new Exception(USER_NOT_FOUND);
    }
  }

  public User getUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));
  }

  public Food getFood(Long foodId) {
    return foodRepository.findById(foodId)
        .orElseThrow(() -> new Exception(FOOD_NOT_FOUND));
  }

  public Favorite getFavorite(Long favoriteId) {
    return favoriteRepository.findById(favoriteId)
        .orElseThrow(() -> new Exception(FAVORITE_NOT_FOUND));
  }

  public Meal getMeal(Long mealId) {
    return mealRepository.findById(mealId)
        .orElseThrow(() -> new Exception(MEAL_NOT_FOUND));
  }

  public MealFood getMealFood(Long mealFoodId) {
    return mealFoodRepository.findById(mealFoodId)
        .orElseThrow(() -> new Exception(MEAL_FOOD_NOT_FOUND));
  }

  public DailyDiet getDailyDiet(Long dailyDietId){
    return dailyDietRepository.findById(dailyDietId)
        .orElseThrow(() -> new Exception(DAILY_DIET_NOT_FOUND));
  }

  public DailyMeal getDailyMeal(Long dailyMealId){
    return dailyMealRepository.findById(dailyMealId)
        .orElseThrow(() -> new Exception(DAILY_MEAL_NOT_FOUND));
  }

  public WeightRecord getWeightRecord(Long weightRecordId){
    return weightRecordRepository.findById(weightRecordId)
        .orElseThrow(() -> new Exception(WEIGHT_RECORD_NOT_FOUND));
  }


}
