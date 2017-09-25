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

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        BlogDao blogDao = new SparkBlogDao();

        before((req, res) -> {
            if (req.cookie("username") != null) {
                req.attribute("username", req.cookie("username"));
            }
        });

        before("/new", (req, res) -> {
            String username = "admin";
            if (req.attribute("username") == null || !(req.attribute("username").equals(username))) {
                res.redirect("/sign-in");
                halt();
            }
        });

        before("/edit/:slug", (req, res) -> {
            String username = "admin";
            if (req.attribute("username") == null || !(req.attribute("username").equals(username))) {
                res.redirect("/sign-in");
                halt();
            }
        });

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", blogDao.findAllEntries());
            model.put("username", req.attribute("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("username"));
            return new ModelAndView(null, "new.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sign-in", (req, res) -> {
            return new ModelAndView(null, "sign-in.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            res.cookie("username", username);
            model.put("username", username);
            res.redirect("/");
            return null;
        });

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
            res.redirect("/detail/" + blogEntry.getSlug());
            return null;
        });

        get("/edit/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", blogDao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit/:slug", (req, res) -> {
            String title = req.queryParams("title");
            String entry = req.queryParams("entry");
            BlogEntry blogEntry = blogDao.findEntryBySlug(req.params("slug"));
            blogEntry.setTitle(title);
            blogEntry.setEntry(entry);
            blogEntry.setDate(LocalDateTime.now());
            res.redirect("/detail/" + blogEntry.getSlug());
            return null;
        });

        post("/delete/:slug", (req, res) -> {
            BlogEntry blogEntry = blogDao.findEntryBySlug(req.params("slug"));
            blogDao.removeEntry(blogEntry);
            res.redirect("/");
            return null;
        });
    }
}
