package org.example.blogproject.model.request;

public record PostRequest(
    String title,
    String content,
    String author,
    String category
) {}
