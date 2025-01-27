package com.rlj.dietAssist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDto {

  @NotBlank(message = "이메일을 입력해주세요.")
  @Email(message = "유효한 이메일을 입력해주세요.")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 입력해주세요.")
  private String password;

  @NotBlank(message = "이름을 입력해주세요.")
  private String name;

  @NotBlank(message = "전화번호를 입력해주세요.")
  @Size(min = 9, max = 11, message = "전화번호는 9자 이상 11자 이하로 입력해주세요.")
  private String phone;

  @NotBlank(message = "신장을 입력해주세요.")
  private float height;

  @NotBlank(message = "체중을 입력해주세요.")
  private float weight;


}
