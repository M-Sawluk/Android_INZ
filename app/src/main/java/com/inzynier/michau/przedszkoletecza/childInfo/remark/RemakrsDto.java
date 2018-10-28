package com.inzynier.michau.przedszkoletecza.childInfo.remark;


import org.threeten.bp.LocalDate;

public class RemakrsDto {
    private final long id;
    private final boolean isPositive;
    private final String author;
    private final String comment;
    private final boolean isRead;
    private final LocalDate date;

    public RemakrsDto(long id, boolean isPositive, String author, String comment, boolean isRead, LocalDate date) {
        this.id = id;
        this.isPositive = isPositive;
        this.author = author;
        this.comment = comment;
        this.isRead = isRead;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDate getDate() {
        return date;
    }
}
