package org.example.blogproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.blogproject.converter.PostConverter;
import org.example.blogproject.global.exceptions.PostException;
import org.example.blogproject.model.entity.Post;
import org.example.blogproject.model.request.PostRequest;
import org.example.blogproject.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public class PostServiceTest {

  @Mock
  private PostRepository postRepository;

  @Mock
  private PostConverter postConverter;

  @InjectMocks
  private PostService postService;

  private Post post;
  private PostRequest postRequest;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    post = new Post(1L,"Title", "Content", "Author", "Category", 0);

    postRequest = new PostRequest(
           "Updated Title",
        "Updated Content",
         "Updated Author",
        "Updated Category");
  }

  @Test
  public void testGetAllPosts() {
    // Given
    List<Post> posts = Arrays.asList(post);
    Page<Post> page = new PageImpl<>(posts);
    Pageable pageable = PageRequest.of(0, 10);

    // When
    when(postRepository.findAll(pageable)).thenReturn(page);

    // Then
    Page<Post> result = postService.getAllPosts(pageable, null, null);

    assertEquals(1, result.getTotalElements());
    verify(postRepository, times(1)).findAll(pageable);
  }

  @Test
  public void testCreatePost() {
    // Given
    when(postConverter.convertToEntity(any(PostRequest.class))).thenReturn(post);
    when(postRepository.save(any(Post.class))).thenReturn(post);

    // When
    Post result = postService.createPost(postRequest);

    // Then
    assertNotNull(result);
    assertEquals(post.getTitle(), result.getTitle());
    verify(postRepository, times(1)).save(any(Post.class));
  }

  @Test
  public void testUpdatePost() {
    // Given
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

    // When
    Post result = postService.updatePost(1L, postRequest);

    // Then
    assertNotNull(result);
    assertEquals(postRequest.title(), result.getTitle());
    verify(postRepository, times(1)).findById(anyLong());
  }

  @Test
  public void testUpdatePost_NotFound() {
    // Given
    when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When & Then
    PostException exception = assertThrows(PostException.class, () -> postService.updatePost(1L, postRequest));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("해당 ID의 게시물을 찾을 수 없습니다.", exception.getStatusText());
    verify(postRepository, times(1)).findById(anyLong());
  }

  @Test
  public void testDeletePost() {
    // Given
    when(postRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(postRepository).deleteById(anyLong());

    // When
    postService.deletePost(1L);

    // Then
    verify(postRepository, times(1)).deleteById(anyLong());
  }

  @Test
  public void testDeletePost_NotFound() {
    // Given
    when(postRepository.existsById(anyLong())).thenReturn(false);

    // When & Then
    PostException exception = assertThrows(PostException.class, () -> postService.deletePost(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("해당 ID의 게시물을 찾을 수 없습니다.", exception.getStatusText());
    verify(postRepository, times(1)).existsById(anyLong());
  }
}
