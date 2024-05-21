package org.example.blogproject.entity.request;

public record PostRequest(
    String title,
    String content,
    String author,
    String category
) {}
