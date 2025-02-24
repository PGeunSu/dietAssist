package com.rlj.dietAssist.controller.meal;

import com.rlj.dietAssist.dto.daily.DailyDietCreateDto;
import com.rlj.dietAssist.dto.daily.DailyDietDto;
import com.rlj.dietAssist.entity.diet.DailyDiet;
import com.rlj.dietAssist.jwt.CustomUserDetails;
import com.rlj.dietAssist.service.meal.DailyDietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dailyDiet")
@RequiredArgsConstructor
public class DailyDietController {

  private final DailyDietService dailyDietService;

  @PostMapping("/create")
  public ResponseEntity<?> createDailyDiet(@AuthenticationPrincipal CustomUserDetails user, @RequestBody DailyDietCreateDto dto){
    DailyDiet dailyDiet = dailyDietService.addDailyMeal(user.getId(), dto);

    return ResponseEntity.ok(new DailyDietDto(dailyDiet));
  }

  @GetMapping("/{dailyDietId}/info")
  public ResponseEntity<?> getDailyMeal(@PathVariable Long dailyDietId){
    DailyDietDto dailyMealDto = dailyDietService.getDailyMeal(dailyDietId);

    return ResponseEntity.ok(dailyMealDto);
  }

  @DeleteMapping("/{dailyMealId}/delete")
  public ResponseEntity<?> deleteDailyMeal(@AuthenticationPrincipal CustomUserDetails user, Long dailDietId, @PathVariable Long dailyMealId){
    dailyDietService.deleteDailyMeal(user.getId(), dailDietId, dailyMealId);

    return ResponseEntity.noContent().build();
  }


  @DeleteMapping("/{dailyDietId}/deleteAll")
  public ResponseEntity<?> deleteDailyDiet(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long dailyDietId){
    dailyDietService.deleteDailyDiet(user.getId(), dailyDietId);

    return ResponseEntity.noContent().build();
  }


}
