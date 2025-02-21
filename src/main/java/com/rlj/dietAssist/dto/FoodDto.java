package com.rlj.dietAssist.dto;

import com.rlj.dietAssist.entity.diet.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
        id, food.getName(),
        food.getWeight(),
        food.getEnergy(),
        food.getCarbohydrate(),
        food.getSugar(),
        food.getProtein(),
        food.getFat()
    );
  }


  }

