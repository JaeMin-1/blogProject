package org.example.blogproject.global.types;

import org.springframework.http.HttpStatus;

public interface ErrorType {

  HttpStatus getStatus();

  String getMessage();
}
