package com.rlj.dietAssist.dto.user;

import lombok.Data;

@Data
public class ChangedPassword {

  private String password;
  private String resetPassword;
  private String confirmPassword;

}
