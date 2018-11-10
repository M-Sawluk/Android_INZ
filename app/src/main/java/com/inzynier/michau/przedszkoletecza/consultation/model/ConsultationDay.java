package com.inzynier.michau.przedszkoletecza.consultation.model;

import org.threeten.bp.LocalDate;

import java.io.Serializable;
import java.util.List;

public class ConsultationDay implements Serializable {
    private long id;
    private LocalDate localDate;
    private List<ConsultationHours> hours;

    public ConsultationDay(long id, LocalDate localDate, List<ConsultationHours> hours) {
        this.id = id;
        this.localDate = localDate;
        this.hours = hours;
    }

    public long getId() {
        return id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public List<ConsultationHours> getHours() {
        return hours;
    }
}
