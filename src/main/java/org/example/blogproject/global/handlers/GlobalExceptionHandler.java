package org.example.blogproject.global.handlers;

import org.example.blogproject.global.exceptions.PostException;
import org.example.blogproject.global.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PostException.class)
  public ResponseEntity<?> handlePostException(PostException e) {
    return ResponseUtil.error(e);
  }

}
