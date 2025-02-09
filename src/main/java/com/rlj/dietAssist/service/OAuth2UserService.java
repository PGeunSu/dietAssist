package com.rlj.dietAssist.service;

import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.jwt.JwtTokenProvider;
import com.rlj.dietAssist.dto.KakaoUserInfoDto;
import com.rlj.dietAssist.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2UserService {

  private final String KAKAO_CLIENT_ID;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  public OAuth2UserService(@Value("${spring.api.kakao}")String KAKAO_CLIENT_ID, UserRepository userRepository,
      JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
    this.KAKAO_CLIENT_ID = KAKAO_CLIENT_ID;
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.passwordEncoder = passwordEncoder;
  }

  public String kakaoLogin(String code){
// 1. 카카오에서 액세스 토큰 요청
//    String accessToken = getKakaoAccessToken(code);

    // 2. 액세스 토큰을 사용하여 카카오 사용자 정보 요청
//    KakaoUserInfoDto kakaoUser = getKakaoUserInfo(accessToken);
      KakaoUserInfoDto kakaoUser = getKakaoUserInfo(code);


    // 3. 기존 회원 여부 확인
    User user = userRepository.findByEmail(kakaoUser.getEmail())
        .orElseGet(() -> registerNewUser(kakaoUser));

    // 4. JWT 토큰 생성 및 반환
    return jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getName());

  }

  @Transactional
  private User registerNewUser(KakaoUserInfoDto kakaoUser) {
    User newUser = User.builder()
        .email(kakaoUser.getEmail())
        .name(kakaoUser.getNickname())
        .password(passwordEncoder.encode(UUID.randomUUID().toString()))  // 랜덤 비밀번호 설정
        //임시 설정
        .phone("000-0000-0000")
        .height(0)
        .weight(0)
        .changed(true)
        .build();

    return userRepository.save(newUser);
  }




  public String getKakaoAccessToken(String code){

    String tokenUrl = "https://kauth.kakao.com/oauth/token";

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", KAKAO_CLIENT_ID);
    body.add("redirect_uri", "http://localhost:8080/oauth2/kakao/login");
    body.add("code", code);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

    return (String) response.getBody().get("access_token");

  }

  public KakaoUserInfoDto getKakaoUserInfo(String accessToken){
    String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<Map<String, Object>> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request,
        new ParameterizedTypeReference<Map<String, Object>>() {});

    Map<String, Object> userInfo = response.getBody();

    if (userInfo == null) {
      throw new RuntimeException("카카오 API 응답이 없습니다.");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> kakaoAccount = objectMapper.convertValue(
        userInfo.get("kakao_account"), new TypeReference<Map<String, Object>>() {}
    );

    Map<String, Object> profile = objectMapper.convertValue(
        kakaoAccount.get("profile"), new TypeReference<Map<String, Object>>() {}
    );

    return KakaoUserInfoDto.builder()
        .id((Long) userInfo.get("id"))  // ID는 항상 존재
        .email(Optional.ofNullable(kakaoAccount.get("email")).map(Object::toString).orElse(null))
        .nickname(Optional.ofNullable(profile.get("nickname")).map(Object::toString).orElse(null))
        .build();
  }

}
