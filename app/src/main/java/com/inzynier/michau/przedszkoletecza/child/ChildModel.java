package com.inzynier.michau.przedszkoletecza.child;

import java.util.Date;


public class ChildModel {
    private final long id;
    private final String name;
    private final String pesel;
    private final String gender;
    private final String date;
    private final int picture;

    public ChildModel(long id, String name, String pesel, String gender, String date, int picture) {
        this.id = id;
        this.name = name;
        this.pesel = pesel;
        this.gender = gender;
        this.date = date;
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPesel() {
        return pesel;
    }

    public String getGender() {
        return gender;
    }

    public int getPicture() { return picture; }

    public String getDate() {
        return date;
    }
}
