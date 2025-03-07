package com.rlj.dietAssist.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDto {

  private Long id;
  private String email;
  private String nickname;

}
