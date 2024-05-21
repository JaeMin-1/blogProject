package org.example.blogproject.global.utils;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

public class ResponseUtil {

  public static <T> ResponseEntity<T> ok() {
    return ResponseEntity.ok().build();
  }

  public static <T> ResponseEntity<T> ok(T result) {
    return ResponseEntity.ok(result);
  }

  public static <T> ResponseEntity<T> created(T result) {
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  public static ResponseEntity<Map<String, String>> error(HttpStatusCodeException e) {
    return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getStatusText()));
  }

}
