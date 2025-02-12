package com.rlj.dietAssist.dto;

import com.rlj.dietAssist.entity.diet.MealFood;
import lombok.AllArgsConstructor;
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
  private float fat;
  private float sugar;

  public MealFoodDto(MealFood mealFood) {
    this.foodName = mealFood.getFood().getName();
    this.weight = mealFood.getWeight();
    this.energy = mealFood.getEnergy();
    this.carbohydrate = mealFood.getCarbohydrate();
    this.protein = mealFood.getProtein();
    this.fat = mealFood.getFat();
    this.sugar = mealFood.getSugar();
  }



}
