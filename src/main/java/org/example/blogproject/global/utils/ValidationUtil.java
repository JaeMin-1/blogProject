package org.example.blogproject.global.utils;

import java.util.List;
import org.example.blogproject.global.exceptions.InvalidDataException;
import org.example.blogproject.global.exceptions.InvalidSortFieldException;
import org.example.blogproject.global.types.PostErrorType;
import org.example.blogproject.model.request.PostForPostRequest;

public class ValidationUtil {

  public static void validatePostRequest(PostForPostRequest postRequest) {
    if (postRequest.title() == null || postRequest.title().isEmpty() ||
        postRequest.content() == null || postRequest.content().isEmpty() ||
        postRequest.author() == null || postRequest.author().isEmpty() ||
        postRequest.category() == null || postRequest.category().isEmpty()) {
      throw new InvalidDataException();
    }
  }

  private static final List<String> ALLOWED_SORT_PROPERTIES = List.of("createdAt", "title", "viewCount");

  public static void validateSortProperty(String sortBy) {
    if (!ALLOWED_SORT_PROPERTIES.contains(sortBy)) {
      throw new InvalidSortFieldException();
    }
  }

}
