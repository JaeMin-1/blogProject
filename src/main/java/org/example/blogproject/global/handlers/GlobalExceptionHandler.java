package org.example.blogproject.global.handlers;

import org.example.blogproject.global.exceptions.InvalidDataException;
import org.example.blogproject.global.exceptions.InvalidSortFieldException;
import org.example.blogproject.global.exceptions.PostNotFoundException;
import org.example.blogproject.global.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<?> handlePostException(PostNotFoundException e) {
    return ResponseUtil.error(e);
  }

  @ExceptionHandler(InvalidSortFieldException.class)
  public ResponseEntity<?> handleInvalidSortFieldException(InvalidSortFieldException e) {
    return ResponseUtil.error(e);
  }

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<?> handleInvalidDataException(InvalidDataException e) {
    return ResponseUtil.error(e);
  }

}
