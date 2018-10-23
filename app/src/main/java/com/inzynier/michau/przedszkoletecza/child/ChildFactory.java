package com.inzynier.michau.przedszkoletecza.child;

import com.inzynier.michau.przedszkoletecza.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChildFactory {

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
}
