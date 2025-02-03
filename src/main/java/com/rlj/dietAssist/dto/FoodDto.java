package com.rlj.dietAssist.dto;

import lombok.Data;

@Data
public class FoodDto {

  private Long id;
  private String foodName;
  private float energy;
  private float carbohydrate;
  private float sugar;
  private float protein;
  private float fat;

  private float saturatedFat; //포화지방
  private float unSaturatedFat; //불포화지방


}
