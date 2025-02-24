package com.rlj.dietAssist.dto.daily;


import com.rlj.dietAssist.entity.diet.DailyDiet;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyDietDto {

  private LocalDate date;

  private List<DailyMealDto> meals;

  private float totalEnergy;
  private float totalCarbohydrate;
  private float totalProtein;
  private float totalFat;
  private float totalSugar;

  public DailyDietDto(DailyDiet dailyDiet) {
    this.date = dailyDiet.getDate();
    this.meals = dailyDiet.getMeals()
        .stream().map(DailyMealDto::new).toList();
    this.totalEnergy = (float) meals.stream().mapToDouble(DailyMealDto::getTotalEnergy).sum();
    this.totalCarbohydrate = (float) meals.stream().mapToDouble(DailyMealDto::getTotalCarbohydrate).sum();
    this.totalProtein = (float) meals.stream().mapToDouble(DailyMealDto::getTotalProtein).sum();
    this.totalFat = (float) meals.stream().mapToDouble(DailyMealDto::getTotalFat).sum();
    this.totalSugar = (float) meals.stream().mapToDouble(DailyMealDto::getTotalSugar).sum();
  }

}
