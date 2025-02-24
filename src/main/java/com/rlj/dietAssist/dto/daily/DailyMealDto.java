package com.rlj.dietAssist.dto.daily;

import com.rlj.dietAssist.entity.diet.DailyMeal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyMealDto {

  private String mealName;
  private float totalEnergy;
  private float totalCarbohydrate;
  private float totalProtein;
  private float totalFat;
  private float totalSugar;

  public DailyMealDto(DailyMeal dailyMeal) {
    this.mealName = dailyMeal.getMeal().getName();
    this.totalEnergy = dailyMeal.getTotalEnergy();
    this.totalCarbohydrate = dailyMeal.getTotalCarbohydrate();
    this.totalProtein = dailyMeal.getTotalProtein();
    this.totalFat = dailyMeal.getTotalFat();
    this.totalSugar = dailyMeal.getTotalSugar();
  }
}
