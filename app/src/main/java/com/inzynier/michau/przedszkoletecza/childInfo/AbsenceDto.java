package com.inzynier.michau.przedszkoletecza.childInfo;


import android.os.Parcelable;

import org.threeten.bp.LocalDate;

import java.io.Serializable;

public class AbsenceDto implements Serializable {
    private final long id;
    private final LocalDate absenceDate;
    private final String content;

    public AbsenceDto(long id, LocalDate absenceDate, String content) {
        this.id = id;
        this.absenceDate = absenceDate;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public String getContent() {
        return content;
    }
}
