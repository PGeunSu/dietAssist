package com.rlj.dietAssist.entity.user;

import com.rlj.dietAssist.dto.auth.SignUpDto;
import com.rlj.dietAssist.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String password;
  private String name;
  private String phone;

  //신체 정보
  private float height;
  private float weight;

  @Setter
  private boolean changed; //카카오로 간편가입할 경우 비밀번호 초기화 가능


  public static User from(SignUpDto dto, PasswordEncoder passwordEncoder){
    return User.builder()
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .name(dto.getName())
        .phone(dto.getPhone())
        .height(dto.getHeight())
        .weight(dto.getWeight())
        .changed(false)
        .build();
  }

  public void modify(String name, String phone, float height, float weight){
    this.name = name;
    this.phone = phone;
    this.height = height;
    this.weight = weight;
  }

  public void changePassword(String password){
    this.password = password;
  }

  public void updateWeight(float weight){
    this.weight = weight;
  }



}
