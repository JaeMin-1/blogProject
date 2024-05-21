package org.example.blogproject.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.blogproject.converter.PostConverter;
import org.example.blogproject.entity.Post;
import org.example.blogproject.entity.request.PostRequest;
import org.example.blogproject.global.exceptions.PostNotFoundException;
import org.example.blogproject.global.types.PostNotFoundErrorType;
import org.example.blogproject.global.utils.ResponseUtil;
import org.example.blogproject.repository.PostRepository;
import org.example.blogproject.global.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostRepository postRepository;
  private final PostConverter postConverter;

  public PostController(PostRepository postRepository, PostConverter postConverter) {
    this.postRepository = postRepository;
    this.postConverter = postConverter;
  }

  // 블로그 포스트를 조회하는 GET 메서드
  @GetMapping
  public Page<Post> getAllPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      @RequestParam(required = false) String searchTerm,
      @RequestParam(required = false) String category) {

    // 정렬 가능한 필드만 정렬하도록 제한
    ValidationUtil.validateSortProperty(sortBy);

    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);

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

  // 새로운 블로그 포스트를 생성하는 POST 메서드
  @PostMapping
  public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
    ValidationUtil.validatePostRequest(postRequest);

    Post post = postConverter.convertToEntity(postRequest);
    Post savedPost = postRepository.save(post);
    return ResponseUtil.created(savedPost);
  }

  // 블로그 포스트를 수정하는 PUT 메서드
  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
    ValidationUtil.validatePostRequest(postRequest);
    Optional<Post> optionalPost = postRepository.findById(id);

    if (optionalPost.isPresent()) {
      Post existingPost = optionalPost.get();
      postConverter.updateEntity(existingPost, postRequest);

      return ResponseUtil.ok(existingPost);
    } else {
      throw new PostNotFoundException(PostNotFoundErrorType.POST_NOT_FOUND);
    }
  }

  // 블로그 포스트를 삭제하는 DELETE 메서드
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    if (postRepository.existsById(id)) {
      postRepository.deleteById(id);
      return ResponseUtil.ok();
    } else {
      throw new PostNotFoundException(PostNotFoundErrorType.POST_NOT_FOUND);
    }
  }
}
