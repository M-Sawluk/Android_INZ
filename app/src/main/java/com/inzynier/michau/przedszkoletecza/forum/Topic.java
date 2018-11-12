package com.inzynier.michau.przedszkoletecza.forum;

import com.inzynier.michau.przedszkoletecza.consultation.model.User;

import java.io.Serializable;


public class Topic implements Serializable {
    private long id;
    private String title;
    private String content;
    private String creationDate;
    private User author;
    private int recentlyAddedComments;

    public Topic(long id, String title, String content, String creationDate, User author, int recentlyAddedComments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.author = author;
        this.recentlyAddedComments = recentlyAddedComments;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public int getRecentlyAddedComments() {
        return recentlyAddedComments;
    }
}
