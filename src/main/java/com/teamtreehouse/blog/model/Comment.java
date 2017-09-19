package com.teamtreehouse.blog.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, YYYY 'at' HH:mm");
        String formattedDate = date.format(formatter);

        return formattedDate;
    }

    public String getComment() {
        return comment;
    }
}
