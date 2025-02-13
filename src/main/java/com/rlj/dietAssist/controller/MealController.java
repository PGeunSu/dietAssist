package com.rlj.dietAssist.controller;

import com.rlj.dietAssist.dto.MealCreateDto;
import com.rlj.dietAssist.dto.MealDto;
import com.rlj.dietAssist.dto.MealFoodDto;
import com.rlj.dietAssist.entity.diet.Meal;
import com.rlj.dietAssist.entity.diet.MealFood;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.jwt.CustomUserDetails;
import com.rlj.dietAssist.service.MealService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

  private final MealService mealService;

  @PostMapping("/create")
  public ResponseEntity<?> createMeal(@AuthenticationPrincipal CustomUserDetails user, @RequestBody MealCreateDto mealCreateDto){
    Meal meal = mealService.createMeal(user.getId(), mealCreateDto);

    return ResponseEntity.ok(meal);
  }

  @GetMapping("/{mealId}/info")
  public ResponseEntity<?> getMeal(@PathVariable Long mealId){
    MealDto mealFoods = mealService.getMeal(mealId);

    return ResponseEntity.ok(mealFoods);
  }

}
