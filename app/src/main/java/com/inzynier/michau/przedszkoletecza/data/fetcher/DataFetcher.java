package com.inzynier.michau.przedszkoletecza.data.fetcher;

import android.app.Activity;
import android.content.Context;

import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.news.factory.NewsFactory;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

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

        String finalUrl = "http://ec2-35-180-135-145.eu-west-3.compute.amazonaws.com:8080/tecza/rest/" + url;
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

    public void fetchBalanceStatus() {
        try {
            Response response = makeRequest("parent/getBalance/1");
            if (response.isSuccessful()) {
                String amount = response.body().string();
                JSONObject jsonObject = new JSONObject(amount);
                String balance = jsonObject.getString("balance");
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .putString("balance", balance)
                        .apply();
            }
        } catch (JSONException | IOException e) {
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

    public void fetchAbsenceRecords() {
        try {
            Response response = makeRequest("childinfo/getAbsenceRecords/1");
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

    public static BigDecimal getBalance(Activity activity) {
        return new BigDecimal(
                activity
                .getSharedPreferences("data", Context.MODE_PRIVATE)
                .getString("balance", "-1")
        );
    }

    public static List<ChildModel> getChildren(Activity activity) throws JSONException {
        return ChildInfoFactory
                .createChildren(
                        activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .getString("children", "")
                );
    }

    public static List<NewsModel> getTrimmedNews(Activity activity) throws JSONException {
        return NewsFactory
                .createTrimmedAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("messages", "")
                );
    }

    public static List<NewsModel> getTrimmmedAnnouncement(Activity activity) throws JSONException {
        return NewsFactory
                .createTrimmedAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("announcement", "")
                );
    }

    public static List<NewsModel> getFullNews(Activity activity) throws JSONException {
        return NewsFactory
                .createFullAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("messages", "")
                );
    }

    public static List<NewsModel> getFullAnnouncement(Activity activity) throws JSONException {
        return NewsFactory
                .createFullAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("announcement", "")
                );
    }

    public static List<AbsenceDto> getAbsenceRecords(Activity activity) {
        return ChildInfoFactory
                .createAbsences(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("absenceRecords", "")
                );
    }
}
