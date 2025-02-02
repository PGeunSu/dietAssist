package com.rlj.dietAssist.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

  @NotBlank(message = "이메일을 입력해주세요.")
  @Email(message = "유효한 이메일을 입력해주세요.")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;


}
