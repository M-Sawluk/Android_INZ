package com.inzynier.michau.przedszkoletecza.childInfo.trusted.ppl;

public class TrustedPersonModel {
    private final long id;
    private final String name;
    private final String surname;
    private final String civilId;
    private final String phone;

    public TrustedPersonModel(long id, String name, String surname, String civilId, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.civilId = civilId;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCivilId() {
        return civilId;
    }

    public String getPhone() {
        return phone;
    }
}
