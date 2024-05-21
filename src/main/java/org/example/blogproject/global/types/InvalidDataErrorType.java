package org.example.blogproject.global.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InvalidDataErrorType implements ErrorType{

  INVALID_DATA(HttpStatus.BAD_REQUEST, "모든 필드를 채워야 합니다.");

  private final HttpStatus status;
  private final String message;

}
