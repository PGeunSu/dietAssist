package com.rlj.dietAssist.controller;

import com.rlj.dietAssist.dto.LoginDto;
import com.rlj.dietAssist.dto.SignUpDto;
import com.rlj.dietAssist.jwt.JwtDto;
import com.rlj.dietAssist.service.AuthService;
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
  public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDto dto){
    authService.signUp(dto);

    return  ResponseEntity.ok("회원가입 성공");
  }

  @PostMapping("/login")
  public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto requestDto) {
    String token = authService.login(requestDto);
    return ResponseEntity.ok(new JwtDto(token));
  }



}
