package org.example.blogproject.model.request;

import org.example.blogproject.model.entity.Post;

public record PostRequest(
    String title,
    String content,
    String author,
    String category
) {
  public Post toEntity(PostRequest postRequest) {
    return new Post(
        postRequest.title(),
        postRequest.content(),
        postRequest.author(),
        postRequest.category()
    );
  }
}
