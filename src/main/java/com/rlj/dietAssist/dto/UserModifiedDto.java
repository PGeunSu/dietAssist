package com.rlj.dietAssist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserModifiedDto {

  @NotBlank(message = "이름은 필수 입력 값입니다.")
  private String name;

  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  private String password;

  @NotBlank(message = "번호는 필수 입력 값입니다.")
  private String phone;

  @NotNull(message = "신장은 필수 입력 값입니다.")
  private float height;

  @NotNull(message = "체중은 필수 입력 값입니다.")
  private float weight;

}
