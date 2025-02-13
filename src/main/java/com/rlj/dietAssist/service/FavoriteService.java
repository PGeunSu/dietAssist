package com.rlj.dietAssist.service;


import static com.rlj.dietAssist.exception.ErrorCode.ALREADY_EXISTING_FAVORITE;
import static com.rlj.dietAssist.exception.ErrorCode.FAVORITE_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;

import com.rlj.dietAssist.dto.FavoriteDto;
import com.rlj.dietAssist.dto.FoodDto;
import com.rlj.dietAssist.entity.diet.Food;
import com.rlj.dietAssist.entity.favorite.Favorite;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.BaseException;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.FavoriteRepository;
import com.rlj.dietAssist.repository.FoodRepository;
import com.rlj.dietAssist.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final BaseException baseException;

  @Transactional
  public FavoriteDto addFavorite(Long userId, Long foodId, String notes) {
    User user = baseException.getUser(userId);
    Food food = baseException.getFood(foodId);

    //식품은 하나의 즐겨찾기만 가능
    if (favoriteRepository.existsByFoodId(foodId)){
      throw new Exception(ALREADY_EXISTING_FAVORITE);
    }

    Favorite favorite = Favorite.builder()
        .user(user)
        .food(food)
        .notes(notes)
        .build();

    Favorite saveFavorite =  favoriteRepository.save(favorite);

    return FavoriteDto.from(saveFavorite);

  }

  @Transactional
  public void deleteFavorite(Long userId, Long foodId) {
    Favorite favorite = favoriteRepository.findByUserIdAndFoodId(userId, foodId)
        .orElseThrow(() -> new Exception(FAVORITE_NOT_FOUND));

    favoriteRepository.delete(favorite);
  }

  //사용자의 즐겨찾기 목록 조회
  public List<FavoriteDto> getFavorites(Long userId) {
    List<Favorite> favorites = favoriteRepository.findByUserId(userId);

    return favorites.stream()
        .map(FavoriteDto::from)
        .collect(Collectors.toList());
  }


}
