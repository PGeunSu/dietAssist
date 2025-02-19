package com.rlj.dietAssist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterFood {

  @NotBlank(message = "식품 이름을 입력해주세요.")
  private String foodName;

  @NotBlank(message = "즐겨찾기 설명을 입력해주세요.")
  private String notes;

  @NotNull(message = "중량을 입력해주세요.")
  private int weight;

  @NotNull(message = "탄수량을 입력해주세요.")
  private float carbohydrate;

  private float energy;

  private float sugar; //당류는 Null 값 허용

  @NotNull(message = "단백질량을 입력해주세요.")
  private float protein;

  @NotNull(message = "지방량을 입력해주세요.")
  private float fat;


  public RegisterFood(RegisterFood registerFood) {
    this.foodName = registerFood.getFoodName();
    this.notes = registerFood.getNotes();
    this.weight = registerFood.getWeight();
    this.carbohydrate = registerFood.getCarbohydrate();
    this.sugar = registerFood.getSugar();
    this.protein = registerFood.getProtein();
    this.fat = registerFood.getFat();
    this.energy = registerFood.getEnergy() == 0 ?
        calculateEnergy(registerFood.getCarbohydrate(), registerFood.getProtein(), registerFood.getFat()) : registerFood.getEnergy();

  }


  private float calculateEnergy(float carbohydrate, float protein, float fat){
    return (carbohydrate * 4) + (protein * 4) + (fat * 9);
  }

}
