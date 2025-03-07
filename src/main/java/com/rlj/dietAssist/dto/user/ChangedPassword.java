package com.rlj.dietAssist.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangedPassword {

  private String password;
  private String resetPassword;
  private String confirmPassword;

  public static ChangedPassword of(ChangedPassword dto){
    return ChangedPassword.builder()
        .password(dto.getPassword())
        .resetPassword(dto.getResetPassword())
        .confirmPassword(dto.getConfirmPassword())
        .build();
  }

}
