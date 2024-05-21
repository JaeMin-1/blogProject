package org.example.blogproject.model.response;

import java.time.LocalDateTime;

public record PostResponse(
    Long id,
    String title,
    String content,
    String author,
    String category,
    int viewCount,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {}
