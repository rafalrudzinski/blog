package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.exception.NotFoundException;
import com.teamtreehouse.blog.model.BlogEntry;

import java.util.ArrayList;
import java.util.List;

public class SparkBlogDao implements BlogDao {

    private List<BlogEntry> blogEntries;

    public SparkBlogDao() {
        blogEntries = new ArrayList<>();
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return blogEntries.add(blogEntry);
    }

    @Override
    public boolean removeEntry(BlogEntry blogEntry) { return blogEntries.remove(blogEntry); }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst().orElseThrow(NotFoundException::new);
    }
}
