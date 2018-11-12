package com.inzynier.michau.przedszkoletecza.forum;


import org.threeten.bp.LocalDate;

public class Comments {
    private final String fullName;
    private final LocalDate creationDate;
    private final String content;

    public Comments(String fullName, LocalDate creationDate, String content) {
        this.fullName = fullName;
        this.creationDate = creationDate;
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getContent() {
        return content;
    }
}
