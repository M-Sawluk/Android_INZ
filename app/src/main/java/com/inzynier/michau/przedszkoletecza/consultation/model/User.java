package com.inzynier.michau.przedszkoletecza.consultation.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String fullname;

    public User(long id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }
}
