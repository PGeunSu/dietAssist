package com.rlj.dietAssist.controller.auth;


import com.rlj.dietAssist.jwt.JwtDto;
import com.rlj.dietAssist.service.auth.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

  private final OAuth2UserService oAuth2UserService;

  @GetMapping("/kakao/login")
  public ResponseEntity<JwtDto> kakaoLogin(@RequestParam String code){
    String jwtToken = oAuth2UserService.kakaoLogin(code);

    return ResponseEntity.ok(new JwtDto(jwtToken));
  }


}
