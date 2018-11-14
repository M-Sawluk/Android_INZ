package com.inzynier.michau.przedszkoletecza.childInfo.incoming;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;

public class IncomingEvent {
    private final String comment;
    private final CalendarDay date;

    public IncomingEvent(String comment, CalendarDay date) {
        this.comment = comment;
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public CalendarDay getDate() {
        return date;
    }
}
