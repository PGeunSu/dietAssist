package com.rlj.dietAssist.dto;

import lombok.Data;

@Data
public class FoodDto {

  private Long id;
  private String foodName;
  private float weight;
  private float energy;
  private float carbohydrate;
  private float sugar;
  private float protein;
  private float totalFat;




}
