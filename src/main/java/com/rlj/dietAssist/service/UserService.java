package com.rlj.dietAssist.service;

import static com.rlj.dietAssist.exception.ErrorCode.CONFIRM_NOT_MATCH;
import static com.rlj.dietAssist.exception.ErrorCode.PASSWORD_DUPLICATION;
import static com.rlj.dietAssist.exception.ErrorCode.PASSWORD_NOT_MATCH;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;

import com.rlj.dietAssist.dto.user.ChangedPassword;
import com.rlj.dietAssist.dto.user.UserDto;
import com.rlj.dietAssist.dto.user.UserModifiedDto;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  //회원 상세 정보
  public UserDto detail(Long userId){

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    return UserDto.of(user);
  }

  //회원 정보 수정
  @Transactional
  public void update(Long userId, UserModifiedDto dto){

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    //비밀번호 일치 여부 확인(카카오 간편가입일 경우 비밀번호를 묻지않음)
    if (!checkPassword(dto.getPassword(), user.getPassword()) && !user.isChanged()){
      throw new Exception(PASSWORD_NOT_MATCH);
    }

    User updateUser = User.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(dto.getName())
        .password(passwordEncoder.encode(dto.getPassword()))
        .phone(dto.getPhone())
        .weight(dto.getWeight())
        .height(dto.getHeight())
        .build();

    userRepository.save(updateUser);
  }

  //회원 삭제
  @Transactional
  public void delete(Long userId, String password){
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    //비밀번호 일치 여부 확인
    if (!checkPassword(password, user.getPassword())){
      throw new Exception(PASSWORD_NOT_MATCH);
    }

    userRepository.deleteById(userId);
  }

  @Transactional
  public void changePassword(Long userId, ChangedPassword dto){
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    if (!checkPassword(dto.getPassword(), user.getPassword())){
      throw new Exception(PASSWORD_NOT_MATCH);
    }else if (dto.getPassword().equals(dto.getResetPassword())){
      throw new Exception(PASSWORD_DUPLICATION);
    }else if (!dto.getResetPassword().equals(dto.getConfirmPassword())){
      throw new Exception(CONFIRM_NOT_MATCH);
    }

    user.changePassword(passwordEncoder.encode(dto.getResetPassword()));

    userRepository.save(user);
  }

  //비밀번호 확인
  public boolean checkPassword(String password, String encodedPassword){
    return passwordEncoder.matches(password, encodedPassword);
  }















}
