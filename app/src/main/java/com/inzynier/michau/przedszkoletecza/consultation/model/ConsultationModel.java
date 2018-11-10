package com.inzynier.michau.przedszkoletecza.consultation.model;

import java.io.Serializable;
import java.util.List;

public class ConsultationModel implements Serializable {
    private User user;
    private List<ConsultationDay> days;

    public ConsultationModel(User user, List<ConsultationDay> days) {
        this.user = user;
        this.days = days;
    }

    public User getUser() {
        return user;
    }

    public List<ConsultationDay> getDays() {
        return days;
    }
}
