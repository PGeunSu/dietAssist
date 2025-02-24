package com.rlj.dietAssist.dto.user;

import com.rlj.dietAssist.entity.user.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteDto {

  private Long id;
  private String userName;
  private String foodName;
  private String notes;

  public static FavoriteDto from(Favorite favorite){
    return new FavoriteDto(
        favorite.getId(),
        favorite.getUser().getName(),
        favorite.getFood().getName(),
        favorite.getNotes()
    );
  }




}
