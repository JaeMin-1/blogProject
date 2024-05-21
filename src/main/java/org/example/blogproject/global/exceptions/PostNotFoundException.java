package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.PostNotFoundErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class PostNotFoundException extends HttpStatusCodeException {

  public PostNotFoundException(PostNotFoundErrorType errorType) {
    super(errorType.getStatus(), errorType.getMessage());
  }
}
