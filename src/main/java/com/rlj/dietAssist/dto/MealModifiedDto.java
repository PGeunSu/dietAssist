package com.rlj.dietAssist.dto;

import lombok.Getter;

@Getter
public class MealModifiedDto {

  private Long mealId;
  private Long mealFoodId;
  private Long foodId;
  private int weight;

}
