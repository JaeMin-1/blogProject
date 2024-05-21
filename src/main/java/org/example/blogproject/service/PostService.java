package org.example.blogproject.service;

import java.util.Optional;
import org.example.blogproject.converter.PostConverter;
import org.example.blogproject.global.exceptions.PostException;
import org.example.blogproject.global.types.PostErrorType;
import org.example.blogproject.model.entity.Post;
import org.example.blogproject.model.request.PostRequest;
import org.example.blogproject.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostConverter postConverter;

  public PostService(PostRepository postRepository, PostConverter postConverter) {
    this.postRepository = postRepository;
    this.postConverter = postConverter;
  }

  public Page<Post> getAllPosts(Pageable pageable, String searchTerm, String category) {
    // 검색어와 카테고리가 모두 없는 경우: 모든 포스트를 페이징하여 반환
    if (searchTerm == null && category == null) {
      return postRepository.findAll(pageable);
    }
    // 카테고리만 있는 경우: 카테고리별로 포스트를 페이징하여 반환
    else if (category != null && (searchTerm == null || searchTerm.isEmpty())) {
      return postRepository.findByCategory(category, pageable);
    }
    // 검색어와 카테고리가 모두 있는 경우: 검색어와 카테고리 둘 다 조건에 맞는 포스트를 페이징하여 반환
    else if (category != null) {
      return postRepository.searchByCategory(searchTerm, category, pageable);
    }
    // 검색어만 있는 경우: 검색어 조건에 맞는 포스트를 페이징하여 반환
    else {
      return postRepository.search(searchTerm, pageable);
    }
  }

  public Post createPost(PostRequest postRequest) {
    Post post = postConverter.convertToEntity(postRequest);
    return postRepository.save(post);
  }

  @Transactional
  public Post updatePost(Long id, PostRequest postRequest) {
    Optional<Post> optionalPost = postRepository.findById(id);

    if (optionalPost.isPresent()) {
      Post existingPost = optionalPost.get();
      existingPost.updateEntity(postRequest);
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
