package org.example.blogproject.model.request;

import org.example.blogproject.model.entity.Post;

public record PostForPostRequest(
    String title,
    String content,
    String author,
    String category
) {
  public Post toEntity(PostForPostRequest request) {
    return new Post(
        request.title(),
        request.content(),
        request.author(),
        request.category()
    );
  }
}
