package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.InvalidDataErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidDataException extends HttpStatusCodeException {

  public InvalidDataException(InvalidDataErrorType errorType) {
    super(errorType.getStatus(), errorType.getMessage());
  }

}
