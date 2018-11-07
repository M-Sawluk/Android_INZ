package com.inzynier.michau.przedszkoletecza.data.fetcher;

import android.app.Activity;
import android.content.Context;

import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.childInfo.progress.ChildProgressDto;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.inzynier.michau.przedszkoletecza.news.factory.NewsFactory;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataFetcher {
    private final Activity activity;
    private OkHttpClient httpClient;
    private static final String PREFERENCES_KEY = "APP_SHRED_PREFS";
    private static final String TOKEN = "TOKEN";

    public DataFetcher(Activity activity) {
        this.activity = activity;
        httpClient = new OkHttpClient();
    }

    public Response makeRequest(String url) throws IOException {
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");

        String finalUrl = "http://35.180.163.7:8080/tecza/rest/" + url;
        Request request = new Request
                .Builder()
                .get()
                .addHeader("Authorization", token)
                .url(finalUrl)
                .build();

        return httpClient
                .newCall(request)
                .execute();
    }

    public void fetchBalanceStatus(long childId) {
        try {
            Response response = makeRequest("parent/getBalance/" + String.valueOf(childId));
            if (response.isSuccessful()) {
                String amount = response.body().string();
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("balance", amount)
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchChild() {
        try {
            Response response = makeRequest("parent/getAll");
            if (response != null && response.isSuccessful()) {
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("children", response.body().string())
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchMessages() {
        try {
            Response response = makeRequest("news/getMessages");
            if (response != null && response.isSuccessful()) {
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("messages", response.body().string())
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchAnouncements() {
        try {
            Response response = makeRequest("news/getAnnouncement");
            if (response != null && response.isSuccessful()) {
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("announcement", response.body().string())
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchAbsenceRecords(long id) {
        try {
            Response response = makeRequest("childinfo/getAbsenceRecords/" + id);
            if (response != null && response.isSuccessful()) {
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("absenceRecords", response.body().string())
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchChildRemarks(long id) {
        try {
            Response response = makeRequest("childinfo/getChildRemarks/" + id);
            if (response != null && response.isSuccessful()) {
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("childRemarks", response.body().string())
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchChildProgress(long id) {
        try {
            Response response = makeRequest("childinfo/getProgrressEvaluation/" + id);
            if (response != null && response.isSuccessful()) {
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("progressEvaluation", response.body().string())
                        .apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void fetchStartupData(long id) {
        this.fetchBalanceStatus(id);
        this.fetchAnouncements();
        this.fetchMessages();
        this.fetchAbsenceRecords(id);
        this.fetchChildRemarks(id);
        StorageUtils.setCurrentChildId(activity, id);
    }
}
