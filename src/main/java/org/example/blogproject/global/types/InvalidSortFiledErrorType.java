package org.example.blogproject.global.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InvalidSortFiledErrorType implements ErrorType {

  INVALID_SORT_FILED_ERROR(HttpStatus.BAD_REQUEST, "정렬 가능한 필드가 아닙니다.");

  private final HttpStatus status;
  private final String message;

}
