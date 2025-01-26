package com.rlj.dietAssist.exception;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException {

  private final ErrorCode errorCode;

  public Exception(ErrorCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }


}
