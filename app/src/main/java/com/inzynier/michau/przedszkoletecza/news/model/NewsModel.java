package com.inzynier.michau.przedszkoletecza.news.model;

import java.util.Date;

public class NewsModel {
    private final long id;
    private final String description;
    private final String title;
    private final String author;
    private final Date date;
    private final byte[] image;

    public NewsModel(long id, String description, String title, String author, Date date, byte[] image) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.author = author;
        this.date = date;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public byte[] getImage() { return image; }
}
