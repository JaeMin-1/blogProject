package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.PostErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class PostNotFoundException extends HttpStatusCodeException {

  public PostNotFoundException() {
    super(PostErrorType.POST_NOT_FOUND.getStatus(), PostErrorType.POST_NOT_FOUND.getMessage());
  }

}
