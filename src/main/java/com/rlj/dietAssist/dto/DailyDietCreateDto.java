package com.rlj.dietAssist.dto;


import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class DailyDietCreateDto {

  private List<Long> mealIds;
  private LocalDate date;

}
