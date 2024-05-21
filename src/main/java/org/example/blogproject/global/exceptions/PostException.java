package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.PostErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class PostException extends HttpStatusCodeException {

  public PostException(PostErrorType errorType) {
    super(errorType.getStatus(), errorType.getMessage());
  }

}
