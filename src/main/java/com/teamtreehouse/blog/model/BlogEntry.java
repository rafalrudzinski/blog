package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlogEntry {
    private String title;
    private String date;
    private String entry;
    private String slug;
    private List<Comment> comments;

    public BlogEntry(String title, String date, String entry, List<Comment> comments) {
        this.title = title;
        this.date = date;
        this.entry = entry;
        comments = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public boolean addComment(Comment comment) {
        return comments.add(comment);
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getEntry() {
        return entry;
    }

    public String getSlug() {
        return slug;
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (!title.equals(blogEntry.title)) return false;
        if (!date.equals(blogEntry.date)) return false;
        return entry.equals(blogEntry.entry);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + entry.hashCode();
        return result;
    }
}
