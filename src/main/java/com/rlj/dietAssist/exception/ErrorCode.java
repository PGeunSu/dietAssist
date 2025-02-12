package com.rlj.dietAssist.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  //User
  ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자 입니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
  LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디와 패스워드를 확인해 주세요."),
  PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  PASSWORD_DUPLICATION(HttpStatus.BAD_REQUEST, "사용중인 비밀번호 입니다"),
  CONFIRM_NOT_MATCH(HttpStatus.BAD_REQUEST, "확인 비밀번호가 일치하지 않습니다."),


  //Food
  FOOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 식품정보입니다."),
  ALREADY_EXISTING_FOOD(HttpStatus.BAD_REQUEST, "이미 존재하는 식품정보입니다."),
  GRAM_UNIT_ERROR(HttpStatus.BAD_REQUEST, "그람수는 10g 단위로 확인 가능합니다"),
  AMOUNT_NOT_MATCH(HttpStatus.BAD_REQUEST, "식품과 중량의 수량이 일치하지 않습니다"),



  //Favorite
  FAVORITE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 즐겨찾기 항목입니다."),
  ALREADY_EXISTING_FAVORITE(HttpStatus.BAD_REQUEST, "즐겨찾기 항목중 동일한 정보가 존재합니다.");

  private final HttpStatus httpStatus;
  private final String description;

}
