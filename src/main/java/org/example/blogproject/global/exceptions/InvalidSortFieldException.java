package org.example.blogproject.global.exceptions;

import org.example.blogproject.global.types.PostErrorType;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidSortFieldException extends HttpStatusCodeException {

  public InvalidSortFieldException() {
    super(PostErrorType.INVALID_SORT_FILED_ERROR.getStatus(), PostErrorType.INVALID_SORT_FILED_ERROR.getMessage());
  }

}
