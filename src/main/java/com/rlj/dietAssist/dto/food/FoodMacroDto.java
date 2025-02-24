package com.rlj.dietAssist.dto.food;

import com.rlj.dietAssist.entity.diet.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

  public static FoodMacroDto from(Food food){
    return FoodMacroDto.builder()
        .foodName(food.getName())
        .weight(food.getWeight())
        .energy(food.getEnergy())
        .carbohydrate(food.getCarbohydrate())
        .sugar(food.getSugar())
        .protein(food.getProtein())
        .fat(food.getFat())
        .build();
  }

}
