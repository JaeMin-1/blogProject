package org.example.blogproject.controller;

import org.example.blogproject.global.utils.ResponseUtil;
import org.example.blogproject.global.utils.ValidationUtil;
import org.example.blogproject.model.entity.Post;
import org.example.blogproject.model.request.PostForPostRequest;
import org.example.blogproject.model.response.PostForGetResponse;
import org.example.blogproject.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public Page<PostForGetResponse> getAllPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      @RequestParam(required = false) String searchTerm,
      @RequestParam(required = false) String category) {

    ValidationUtil.validateSortProperty(sortBy);

    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);

    return postService.getAllPosts(pageable, searchTerm, category);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostForGetResponse> getPostById(@PathVariable Long id) {
    PostForGetResponse postResponse = postService.getPostById(id);
    return ResponseUtil.ok(postResponse);
  }

  @PostMapping
  public ResponseEntity<Post> createPost(@RequestBody PostForPostRequest request) {
    ValidationUtil.validatePostRequest(request);
    Post savedPost = postService.createPost(request);
    return ResponseUtil.created(savedPost);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostForPostRequest request) {
    ValidationUtil.validatePostRequest(request);
    Post updatedPost = postService.updatePost(id, request);
    return ResponseUtil.ok(updatedPost);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseUtil.ok();
  }
}
