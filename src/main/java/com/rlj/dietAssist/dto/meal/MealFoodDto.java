package com.rlj.dietAssist.dto.meal;


import com.rlj.dietAssist.entity.diet.MealFood;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MealFoodDto {

  private String foodName;
  private float weight;
  private float energy;
  private float carbohydrate;
  private float protein;
  private float totalFat;
  private float sugar;

  public MealFoodDto(MealFood mealFood) {
    this.foodName = mealFood.getFood().getName();
    this.weight = mealFood.getWeight();
    this.energy = mealFood.getEnergy();
    this.carbohydrate = mealFood.getCarbohydrate();
    this.protein = mealFood.getProtein();
    this.totalFat = mealFood.getFat();
    this.sugar = mealFood.getSugar();
  }

}
