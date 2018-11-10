package com.inzynier.michau.przedszkoletecza.consultation;

import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationDay;
import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationHours;
import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationModel;
import com.inzynier.michau.przedszkoletecza.consultation.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ConsultationFactory {

    public static List<ConsultationModel> create(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);

            List<ConsultationModel> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<ConsultationDay> consultationDaysL = new ArrayList<>();
                ArrayList<ConsultationHours> consultationHoursL = new ArrayList<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject user = jsonObject.getJSONObject("user");
                String fullName = user.getString("name") + " " + user.getString("surname");
                long id = user.getLong("id");
                JSONArray consultationDay = jsonObject.getJSONArray("consultationDay");
                for (int i1 = 0; i1 < consultationDay.length(); i1++) {
                    JSONObject day = consultationDay.getJSONObject(i1);
                    long dayID = day.getLong("id");
                    String consultationDay1 = day.getString("consultationDay");
                    JSONArray consultationHours = day.getJSONArray("consultationHours");
                    for (int i2 = 0; i2 < consultationHours.length(); i2++) {
                        JSONObject hours = consultationHours.getJSONObject(i2);
                        consultationHoursL.add(new ConsultationHours(hours.getInt("hour"), hours.getInt("minutes")));
                    }
                    consultationDaysL.add(new ConsultationDay(dayID, LocalDate.parse(consultationDay1), consultationHoursL));
                }
                list.add(new ConsultationModel(new User(id, fullName), consultationDaysL));
            }

            return list;
        } catch (JSONException e) {
            throw new IllegalArgumentException(" Wrong json" + json);
        }

    }
}
