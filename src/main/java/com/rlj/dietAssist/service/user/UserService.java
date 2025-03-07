package com.rlj.dietAssist.service.user;

import static com.rlj.dietAssist.exception.ErrorCode.CONFIRM_NOT_MATCH;
import static com.rlj.dietAssist.exception.ErrorCode.PASSWORD_DUPLICATION;
import static com.rlj.dietAssist.exception.ErrorCode.PASSWORD_NOT_MATCH;

import com.rlj.dietAssist.dto.user.ChangedPassword;
import com.rlj.dietAssist.dto.user.UserDto;
import com.rlj.dietAssist.dto.user.UserModifiedDto;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.BaseException;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final BaseException baseException;

  //회원 상세 정보
  public UserDto detail(Long userId){

    User user = baseException.getUser(userId);

    return UserDto.of(user);
  }

  //회원 정보 수정
  @Transactional
  public UserDto update(Long userId, UserModifiedDto dto){

    User updateUser = baseException.getUser(userId);

    //비밀번호 일치 여부 확인(카카오 간편가입일 경우 비밀번호를 묻지않음)
    if (!checkPassword(dto.getPassword(), updateUser.getPassword()) && !updateUser.isChanged()){
      throw new Exception(PASSWORD_NOT_MATCH);
    }

    updateUser.modify(dto.getName(), dto.getPhone(), dto.getHeight(), dto.getWeight());

    return UserDto.of(updateUser);
  }

  //회원 삭제
  @Transactional
  public void delete(Long userId, String password){
    User user = baseException.getUser(userId);

    //비밀번호 일치 여부 확인
    if (!checkPassword(password, user.getPassword())){
      throw new Exception(PASSWORD_NOT_MATCH);
    }

    userRepository.deleteById(userId);
  }

  @Transactional
  public void changePassword(Long userId, ChangedPassword dto){
    User user = baseException.getUser(userId);

    if (!checkPassword(dto.getPassword(), user.getPassword())){
      throw new Exception(PASSWORD_NOT_MATCH);
    }

    if (dto.getPassword().equals(dto.getResetPassword())){
      throw new Exception(PASSWORD_DUPLICATION);
    }

    if (!dto.getResetPassword().equals(dto.getConfirmPassword())){
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
