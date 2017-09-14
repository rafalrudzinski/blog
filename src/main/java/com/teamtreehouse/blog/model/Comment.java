package com.teamtreehouse.blog.model;

public class Comment {
    private String author;
    private String date;
    private String comment;

    public Comment(String author, String date, String comment) {
        this.author = author;
        this.date = date;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }
}
