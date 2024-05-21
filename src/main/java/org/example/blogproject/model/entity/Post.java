package org.example.blogproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.blogproject.global.model.entity.TimeStamp;
import org.example.blogproject.model.request.PostForPostRequest;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends TimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false)
  private String author;

  @Column
  private String category;

  @Column
  private int viewCount;

  public Post(String title, String content, String author, String category) {
    this.title = title;
    this.content = content;
    this.author = author;
    this.category = category;
  }

  public void updateEntity(PostForPostRequest postRequest) {
    this.title = postRequest.title();
    this.content = postRequest.content();
    this.author = postRequest.author();
    this.category = postRequest.category();
  }
}
