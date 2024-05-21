package org.example.blogproject.global.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorType implements ErrorType{

  INVALID_DATA(HttpStatus.BAD_REQUEST, "모든 필드를 채워야 합니다."),
  INVALID_SORT_FILED_ERROR(HttpStatus.BAD_REQUEST, "정렬 가능한 필드가 아닙니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 게시물을 찾을 수 없습니다.");

  private final HttpStatus status;
  private final String message;

}
