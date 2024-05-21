package org.example.blogproject.service;

import java.util.Optional;
import org.example.blogproject.global.exceptions.PostException;
import org.example.blogproject.global.types.PostErrorType;
import org.example.blogproject.model.entity.Post;
import org.example.blogproject.model.request.PostForPostRequest;
import org.example.blogproject.model.response.PostForGetResponse;
import org.example.blogproject.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Transactional(readOnly = true)
  public Page<PostForGetResponse> getAllPosts(Pageable pageable, String searchTerm, String category) {
    Page<Post> posts;
    // 검색어와 카테고리가 모두 없는 경우: 모든 포스트를 페이징하여 반환
    if (searchTerm == null && category == null) {
      posts = postRepository.findAll(pageable);
    }
    // 카테고리만 있는 경우: 카테고리별로 포스트를 페이징하여 반환
    else if (category != null && (searchTerm == null || searchTerm.isEmpty())) {
      posts = postRepository.findByCategory(category, pageable);
    }
    // 검색어와 카테고리가 모두 있는 경우: 검색어와 카테고리 둘 다 조건에 맞는 포스트를 페이징하여 반환
    else if (category != null) {
      posts = postRepository.searchByCategory(searchTerm, category, pageable);
    }
    // 검색어만 있는 경우: 검색어 조건에 맞는 포스트를 페이징하여 반환
    else {
      posts = postRepository.search(searchTerm, pageable);
    }
    return posts.map(PostForGetResponse::new);
  }

  @Transactional(readOnly = true)
  public PostForGetResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostException(PostErrorType.POST_NOT_FOUND));
    return new PostForGetResponse(post);
  }

  public Post createPost(PostForPostRequest postRequest) {
    Post post = postRequest.toEntity(postRequest);
    return postRepository.save(post);
  }

  @Transactional
  public Post updatePost(Long id, PostForPostRequest request) {
    Optional<Post> optionalPost = postRepository.findById(id);

    if (optionalPost.isPresent()) {
      Post existingPost = optionalPost.get();
      existingPost.updateEntity(request);
      return existingPost;
    } else {
      throw new PostException(PostErrorType.POST_NOT_FOUND);
    }
  }

  public void deletePost(Long id) {
    if (postRepository.existsById(id)) {
      postRepository.deleteById(id);
    } else {
      throw new PostException(PostErrorType.POST_NOT_FOUND);
    }
  }
}
