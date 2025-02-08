package com.rlj.dietAssist.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.rlj.dietAssist.dto.FoodDto;
import com.rlj.dietAssist.service.FoodService;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {

  private final FoodService foodService;


  @GetMapping("/usda/{fdcId}")
  public ResponseEntity<?> getUsda(@PathVariable String fdcId){
    String foodData = foodService.getUsdaFoodId(fdcId);
    return ResponseEntity.ok(foodData);
  }


  @GetMapping("/usda")
  public ResponseEntity<?> getFood(@RequestParam String foodName){
    List<FoodDto> foodList = foodService.getUsdaFood(foodName);
    return ResponseEntity.ok(foodList);
  }


}
