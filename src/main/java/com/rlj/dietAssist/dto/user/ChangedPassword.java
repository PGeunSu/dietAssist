package com.rlj.dietAssist.dto.user;

import com.rlj.dietAssist.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
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
