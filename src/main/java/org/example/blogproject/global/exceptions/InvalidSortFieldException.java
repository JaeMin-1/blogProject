package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.InvalidSortFiledErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidSortFieldException extends HttpStatusCodeException {

  public InvalidSortFieldException(InvalidSortFiledErrorType errorType) {
    super(errorType.getStatus(), errorType.getMessage());
  }

}
