package com.rlj.dietAssist.exception;

import static com.rlj.dietAssist.exception.ErrorCode.FAVORITE_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.MEAL_FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.MEAL_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;

import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.entity.diet.Meal;
import com.rlj.dietAssist.entity.diet.MealFood;
import com.rlj.dietAssist.entity.favorite.Favorite;
import com.rlj.dietAssist.entity.user.User;
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


}
