package org.example.blogproject.global.converter;

import org.example.blogproject.model.entity.Post;
import org.example.blogproject.model.request.PostRequest;
import org.example.blogproject.model.response.PostResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  public Post convertToEntity(PostRequest postRequest) {
    return new Post(
        postRequest.title(),
        postRequest.content(),
        postRequest.author(),
        postRequest.category()
    );
  }

  public PostResponse convertToResponse(Post post) {
    return new PostResponse(
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
