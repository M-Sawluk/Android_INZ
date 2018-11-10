package com.inzynier.michau.przedszkoletecza.consultation.model;

import java.io.Serializable;

public class ConsultationHours implements Serializable {
    private int hour;
    private int min;

    public ConsultationHours(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }
}
