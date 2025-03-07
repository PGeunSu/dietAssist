package com.rlj.dietAssist.service.meal;

import static com.rlj.dietAssist.exception.ErrorCode.DAILY_MEAL_OVER_SIZE;

import com.rlj.dietAssist.dto.daily.DailyDietCreateDto;
import com.rlj.dietAssist.dto.daily.DailyDietDto;
import com.rlj.dietAssist.entity.diet.DailyDiet;
import com.rlj.dietAssist.entity.diet.DailyMeal;
import com.rlj.dietAssist.entity.diet.Meal;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.BaseException;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.daily.DailyDietRepository;
import com.rlj.dietAssist.repository.daily.DailyMealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyDietService {

  private final BaseException baseException;
  private final DailyDietRepository dailyDietRepository;
  private final DailyMealRepository dailyMealRepository;

  //하루 식단 추가
  @Transactional
  public DailyDiet addDailyMeal(Long userId, DailyDietCreateDto dto){

    //하루 7개까지 추가 가능
    if (dto.getMealIds().size() > 7){
      throw new Exception(DAILY_MEAL_OVER_SIZE);
    }
    User user = baseException.getUser(userId);

    DailyDiet dailyDiet = new DailyDiet();

    dailyDiet.setDailyDiet(user, dto.getDate());

    for (int i = 0; i < dto.getMealIds().size(); i++) {
      Meal meal = baseException.getMeal(dto.getMealIds().get(i));

      DailyMeal dailyMeal = new DailyMeal(dailyDiet, user, meal);

      dailyDiet.addMeal(dailyMeal);
    }

    return dailyDietRepository.save(dailyDiet);
  }

  //하루 식단 조회
  public DailyDietDto getDailyMeal(Long dailyDietId){
    DailyDiet dailyDiet = baseException.getDailyDiet(dailyDietId);

    return new DailyDietDto(dailyDiet);
  }

  //하루 식단 중 한 개의 식단 삭제
  @Transactional
  public void deleteDailyMeal(Long userId, Long dailyDietId, Long dailyMealId){

    baseException.validateUserExists(userId);
    DailyDiet dailyDiet = baseException.getDailyDiet(dailyDietId);
    DailyMeal dailyMeal = baseException.getDailyMeal(dailyMealId);

    dailyDiet.removeMeal(dailyMeal);
    dailyMealRepository.delete(dailyMeal);
  }

  //하루 식단 삭제
  @Transactional
  public void deleteDailyDiet(Long userId, Long dailyDietId){
    baseException.validateUserExists(userId);
    DailyDiet dailyDiet = baseException.getDailyDiet(dailyDietId);

    dailyDietRepository.delete(dailyDiet);
  }






}
