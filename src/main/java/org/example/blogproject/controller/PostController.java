package org.example.blogproject.controller;

import org.example.blogproject.entity.Post;
import org.example.blogproject.entity.request.PostRequest;
import org.example.blogproject.global.utils.ResponseUtil;
import org.example.blogproject.global.utils.ValidationUtil;
import org.example.blogproject.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public Page<Post> getAllPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      @RequestParam(required = false) String searchTerm,
      @RequestParam(required = false) String category) {

    ValidationUtil.validateSortProperty(sortBy);

    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);

    return postService.getAllPosts(sortBy, pageable, searchTerm, category);
  }

  @PostMapping
  public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
    ValidationUtil.validatePostRequest(postRequest);
    Post savedPost = postService.createPost(postRequest);
    return ResponseUtil.created(savedPost);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
    ValidationUtil.validatePostRequest(postRequest);
    Post updatedPost = postService.updatePost(id, postRequest);
    return ResponseUtil.ok(updatedPost);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseUtil.ok();
  }
}
