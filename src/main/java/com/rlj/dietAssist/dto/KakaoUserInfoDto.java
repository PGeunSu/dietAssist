package com.rlj.dietAssist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class KakaoUserInfoDto {

  private Long id;
  private String email;
  private String nickname;

}
