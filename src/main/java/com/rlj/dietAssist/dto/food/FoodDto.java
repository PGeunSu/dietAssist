package com.rlj.dietAssist.dto.food;

import com.rlj.dietAssist.entity.diet.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

  private Long id;
  private String foodName;
  private float weight;
  private float energy;
  private float carbohydrate;
  private float sugar;
  private float protein;
  private float fat;

  public static FoodDto from(Long id, Food food){
    return new FoodDto(
        id,
        food.getName(),
        food.getWeight(),
        food.getEnergy(),
        food.getCarbohydrate(),
        food.getSugar(),
        food.getProtein(),
        food.getFat()
    );
  }

  public static FoodDto getFoodApi(Long id, String foodName, float weight, float energy,
      float carbohydrate, float sugar, float protein, float fat) {
    return new FoodDto(id, foodName, weight, energy, carbohydrate, sugar, protein, fat);
  }
}

