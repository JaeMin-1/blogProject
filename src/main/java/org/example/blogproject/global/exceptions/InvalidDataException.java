package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.PostErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidDataException extends HttpStatusCodeException {

  public InvalidDataException() {
    super(PostErrorType.INVALID_DATA.getStatus(), PostErrorType.INVALID_DATA.getMessage());
  }

}
