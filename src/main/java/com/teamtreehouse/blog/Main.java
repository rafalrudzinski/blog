package com.teamtreehouse.blog;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.SparkBlogDao;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        BlogDao blogDao = new SparkBlogDao();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", blogDao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> {
            return new ModelAndView(null, "new.hbs");
        }, new HandlebarsTemplateEngine());

        get("/detail/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", blogDao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/new", (req, res) -> {
            String title = req.queryParams("title");
            String entry = req.queryParams("entry");
            BlogEntry blogEntry = new BlogEntry(title, entry, LocalDateTime.now());
            blogDao.addEntry(blogEntry);
            res.redirect("/");
            return null;
        });

        post("/detail/:slug", (req, res) -> {
            String author = req.queryParams("name");
            String text = req.queryParams("comment");
            Comment comment = new Comment(author, LocalDateTime.now(), text);
            BlogEntry blogEntry = blogDao.findEntryBySlug(req.params("slug"));
            blogEntry.addComment(comment);
            res.redirect("/detail/:slug");
            return null;
        });
    }
}
