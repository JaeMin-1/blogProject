package org.example.blogproject.converter;

import org.example.blogproject.model.entity.Post;
import org.example.blogproject.model.request.PostRequest;
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

}
