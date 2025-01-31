package com.rlj.dietAssist.service;

import static com.rlj.dietAssist.exception.ErrorCode.ALREADY_REGISTER_USER;
import static com.rlj.dietAssist.exception.ErrorCode.PASSWORD_NOT_MATCH;
import static com.rlj.dietAssist.exception.ErrorCode.USER_NOT_FOUND;

import com.rlj.dietAssist.dto.LoginDto;
import com.rlj.dietAssist.dto.SignUpDto;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.exception.ErrorCode;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.jwt.JwtTokenProvider;
import com.rlj.dietAssist.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public void signUp(SignUpDto dto){
    if (userRepository.existsByEmail(dto.getEmail())){
      throw new Exception(ALREADY_REGISTER_USER);
    }

    User user = User.builder()
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .name(dto.getName())
        .phone(dto.getPhone())
        .height(dto.getHeight())
        .weight(dto.getWeight())
        .changed(false)
        .build();

    userRepository.save(user);

  }

  public String login(LoginDto dto) {

    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new Exception(PASSWORD_NOT_MATCH);
    }

    return jwtTokenProvider.generateToken(user.getEmail());
  }

}
