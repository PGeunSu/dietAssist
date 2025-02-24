package com.rlj.dietAssist.dto.meal;

import com.rlj.dietAssist.entity.diet.Meal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MealDto {

  private String mealName;
  private List<MealFoodDto> mealFoods;
  private float totalEnergy;
  private float totalCarbohydrate;
  private float totalProtein;
  private float totalFat;
  private float totalSugar;

  public MealDto(Meal meal) {
    this.mealName= meal.getName();
    this.mealFoods = meal.getMealFoods()
        .stream().map(MealFoodDto::new).toList();
    this.totalEnergy = (float) mealFoods.stream().mapToDouble(MealFoodDto::getEnergy).sum();
    this.totalCarbohydrate = (float) mealFoods.stream().mapToDouble(MealFoodDto::getCarbohydrate).sum();
    this.totalProtein = (float) mealFoods.stream().mapToDouble(MealFoodDto::getProtein).sum();
    this.totalFat = (float) mealFoods.stream().mapToDouble(MealFoodDto::getTotalFat).sum();
    this.totalSugar = (float) mealFoods.stream().mapToDouble(MealFoodDto::getSugar).sum();
  }

}
