package com.rlj.dietAssist.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MealCreateDto {

  private List<Long> foodIds;
  private List<Integer> weight;
  private String mealName;

}
