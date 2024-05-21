package org.example.blogproject.converter;

import org.example.blogproject.entity.Post;
import org.example.blogproject.entity.request.PostRequest;
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

  public void updateEntity(Post post, PostRequest postRequest) {
    post.setTitle(postRequest.title());
    post.setContent(postRequest.content());
    post.setAuthor(postRequest.author());
    post.setCategory(postRequest.category());
  }

}
