package com.inzynier.michau.przedszkoletecza.data;

import android.app.Activity;
import android.content.Context;

import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.news.factory.NewsFactory;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostRequester {
    private final Activity activity;
    private OkHttpClient httpClient;
    private static final String PREFERENCES_KEY = "APP_SHRED_PREFS";
    private static final String TOKEN = "TOKEN";

    public PostRequester(Activity activity) {
        this.activity = activity;
        httpClient = new OkHttpClient();
    }

    public Response makePostRequest(String url, JSONObject jsonObject) throws IOException {
        String finalUrl = "http://35.180.163.7:8080/tecza/rest/" + url;
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");

        Request request = new Request
                .Builder()
                .post(RequestBody.create(mediaType, jsonObject.toString()))
                .addHeader("Authorization", token)
                .addHeader("User-Agent", "Android")
                .url(finalUrl)
                .build();

        return httpClient
                .newCall(request)
                .execute();
    }

    public Response makePostRequestWithList(String url, List<JSONObject> jsonObject) throws IOException {
        String finalUrl = "http://35.180.163.7:8080/tecza/rest/" + url;
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");
        JSONArray jsonArray = new JSONArray(jsonObject);
        Request request = new Request
                .Builder()
                .post(RequestBody.create(mediaType, jsonArray.toString()))
                .addHeader("Authorization", token)
                .addHeader("User-Agent", "Android")
                .url(finalUrl)
                .build();

        return httpClient
                .newCall(request)
                .execute();
    }
}
