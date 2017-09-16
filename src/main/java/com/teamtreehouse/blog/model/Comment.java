package com.teamtreehouse.blog.model;

import java.time.LocalDateTime;

public class Comment {
    private String author;
    private LocalDateTime date;
    private String comment;

    public Comment(String author, LocalDateTime date, String comment) {
        this.author = author;
        this.date = date;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }
}
