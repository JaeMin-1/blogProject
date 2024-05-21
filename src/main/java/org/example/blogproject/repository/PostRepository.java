package org.example.blogproject.repository;

import org.example.blogproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("SELECT p FROM Post p WHERE " +
      "LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "LOWER(p.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
  Page<Post> search(@Param("searchTerm") String searchTerm, Pageable pageable);

  @Query("SELECT p FROM Post p WHERE " +
      "(LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "LOWER(p.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
      "p.category LIKE LOWER(CONCAT('%', :category, '%'))")
  Page<Post> searchByCategory(@Param("searchTerm") String searchTerm, @Param("category") String category, Pageable pageable);

  Page<Post> findByCategory(@Param("category") String category, Pageable pageable);
}
