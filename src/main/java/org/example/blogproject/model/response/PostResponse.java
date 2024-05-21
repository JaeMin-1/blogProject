package org.example.blogproject.model.response;

import java.time.LocalDateTime;
import org.example.blogproject.model.entity.Post;

public record PostResponse(
    Long id,
    String title,
    String content,
    String author,
    String category,
    int viewCount,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {
  public PostResponse(Post post) {
    this(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getAuthor(),
        post.getCategory(),
        post.getViewCount(),
        post.getCreatedAt(),
        post.getModifiedAt()
    );
  }
}
