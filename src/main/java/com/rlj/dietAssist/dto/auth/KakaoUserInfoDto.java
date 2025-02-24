package com.rlj.dietAssist.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDto {

  private Long id;
  private String email;
  private String nickname;

}
