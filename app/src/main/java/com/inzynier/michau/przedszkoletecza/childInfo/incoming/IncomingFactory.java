package com.inzynier.michau.przedszkoletecza.childInfo.incoming;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class IncomingFactory {

    public static List<IncomingEvent> create(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<IncomingEvent> events = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject incomingEvent = jsonArray.getJSONObject(i);
                String date = incomingEvent.getString("date");
                String comment = incomingEvent.getString("comment");

                events.add(new IncomingEvent(comment, CalendarDay.from(LocalDate.parse(date))));
            }
            return events;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Wrong json");
    }
}
