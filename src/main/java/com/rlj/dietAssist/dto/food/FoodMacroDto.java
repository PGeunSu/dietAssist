package com.rlj.dietAssist.dto.food;

import com.rlj.dietAssist.entity.diet.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodMacroDto {

  private String foodName;
  private float weight;
  private float energy;
  private float carbohydrate;
  private float sugar;
  private float protein;
  private float fat;

  public static FoodMacroDto from(String foodName, float weight, float energy, float carbohydrate, float sugar, float protein, float fat){
    return new FoodMacroDto(foodName, weight, energy, carbohydrate, sugar, protein, fat);
  }

}
