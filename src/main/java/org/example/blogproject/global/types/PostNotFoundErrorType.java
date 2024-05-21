package org.example.blogproject.global.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostNotFoundErrorType implements ErrorType {

  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 게시물을 찾을 수 없습니다.");

  private final HttpStatus status;
  private final String message;


}
