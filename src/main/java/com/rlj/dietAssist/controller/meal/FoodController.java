package com.rlj.dietAssist.controller.meal;

import com.rlj.dietAssist.dto.food.FoodDto;
import com.rlj.dietAssist.dto.food.FoodMacroDto;
import com.rlj.dietAssist.dto.food.RegisterFood;
import com.rlj.dietAssist.jwt.CustomUserDetails;
import com.rlj.dietAssist.service.meal.FoodService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {

  private final FoodService foodService;

  @PostMapping("/save")
  public ResponseEntity<RegisterFood> selfSaveFoodNutrient(@AuthenticationPrincipal CustomUserDetails user, @RequestBody RegisterFood registerFood){

    foodService.selfFoodNutrient(user.getId(), registerFood);

    return ResponseEntity.ok(new RegisterFood(registerFood));
  }

  @PostMapping("/save/{id}")
  public ResponseEntity<FoodDto> saveFoodNutrient(@PathVariable Long id, @RequestParam String foodName){

    FoodDto dto = foodService.saveFoodNutrient(id, foodName);

    return ResponseEntity.ok(dto);
  }


  @GetMapping("/nutrient")
  public ResponseEntity<List<FoodDto>> getFood(@RequestParam String foodName){

    List<FoodDto> foodList = foodService.getFoodNutrients(foodName);

    return ResponseEntity.ok(foodList);
  }



  @GetMapping("/{id}/macro")
  public ResponseEntity<FoodMacroDto> getMacronutrient(@PathVariable Long id, @RequestParam int weight){

    FoodMacroDto macroDto = foodService.getMacronutrients(id, weight);

    return ResponseEntity.ok(macroDto);
  }


}
