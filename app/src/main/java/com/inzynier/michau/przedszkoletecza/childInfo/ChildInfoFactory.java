package com.inzynier.michau.przedszkoletecza.childInfo;

import com.inzynier.michau.przedszkoletecza.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ChildInfoFactory {

    public static List<ChildModel> createChildren(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<ChildModel> children = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String pesel = jsonObject.getString("pesel");
            String date = "20" + pesel.substring(0, 2) + "-" + pesel.substring(2, 4) + "-" + pesel.substring(4, 6);
            String gender = Integer.parseInt(pesel.substring(10, 11)) % 2 == 0 ? "Kobieta" : "Mężczyzna";
            int picture = gender.equals("Kobieta") ? R.drawable.girl : R.drawable.boy;
            ChildModel newsModel = new ChildModel(
                    jsonObject.getLong("id"),
                    jsonObject.getString("name") + " " + jsonObject.getString("surname"),
                    jsonObject.getString("pesel"),
                    gender,
                    date,
                    picture);
            children.add(newsModel);
        }
        return children;
    }

    public static List<AbsenceDto> createAbsences(String json)  {
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<AbsenceDto> absences = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String absenceDate = jsonObject.getString("absenceDate");
                long id = jsonObject.getLong("id");
                String reason = jsonObject.getString("reason");
                absences.add(new AbsenceDto(id, LocalDate.parse(absenceDate) , reason));
            }

            return absences;
        } catch (JSONException e) {

        }
        throw new IllegalArgumentException(" Wrong json" + json);
    }

    public static List<CalendarDay> mapToCalendarDays(List<AbsenceDto> absenceDtos) {
        List<CalendarDay> calendarDays = new ArrayList<>();
        for (AbsenceDto absenceDto : absenceDtos) {
            calendarDays.add(CalendarDay.from(absenceDto.getAbsenceDate()));
        }
        return calendarDays;
    }

}
