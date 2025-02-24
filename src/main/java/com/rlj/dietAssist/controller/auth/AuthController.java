package com.rlj.dietAssist.controller.auth;

import com.rlj.dietAssist.dto.auth.LoginDto;
import com.rlj.dietAssist.dto.auth.SignUpDto;
import com.rlj.dietAssist.dto.user.UserDto;
import com.rlj.dietAssist.jwt.JwtDto;
import com.rlj.dietAssist.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/singUp")
  public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto dto){
    UserDto userDto = authService.signUp(dto);

    return ResponseEntity.ok(userDto);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto requestDto) {
    String token = authService.login(requestDto);
    return ResponseEntity.ok(new JwtDto(token));
  }



}
