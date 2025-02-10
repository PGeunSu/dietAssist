package com.rlj.dietAssist.dto.user;

import com.rlj.dietAssist.entity.user.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class UserDto {

  private String email;
  private String name;
  private String phone;

  private float height;
  private float weight;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public static UserDto of(User user){
    return UserDto.builder()
        .email(user.getEmail())
        .name(user.getName())
        .phone(user.getPhone())
        .height(user.getHeight())
        .weight(user.getWeight())
        .createdAt(user.getCreatedAt())
        .modifiedAt(user.getModifiedAt())
        .build();
  }





}
