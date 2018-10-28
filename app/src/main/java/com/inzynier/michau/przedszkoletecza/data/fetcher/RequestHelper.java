package com.inzynier.michau.przedszkoletecza.data.fetcher;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class RequestHelper {
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final String PREFERENCES_KEY = "APP_SHRED_PREFS";
    private static final String TOKEN = "TOKEN";

    public static Response makeRequest(String url, Activity activity) throws IOException {
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
}
