package com.inzynier.michau.przedszkoletecza;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.child.ChildModel;
import com.inzynier.michau.przedszkoletecza.news.adapter.AnnouncmentAdapter;
import com.inzynier.michau.przedszkoletecza.news.adapter.NewsAdapter;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.droidsonroids.gif.GifImageView;

public class SliderAdapter extends PagerAdapter {
    Activity activity;
    Context context;
    LayoutInflater layoutInflater;
    private OkHttpClient httpClient = new OkHttpClient();
    private String PREFERENCES_KEY = "APP_SHRED_PREFS";
    private String TOKEN = "TOKEN";

    public SliderAdapter(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
    }

    private int[] gifs = {R.drawable.cashok, R.drawable.boy, R.drawable.cashok, R.drawable.cashok, R.drawable.cashwarning};

    private int[] colors = {Color.parseColor("#FF0A8730"), Color.BLACK, Color.BLACK, Color.BLACK, Color.RED};

    private String[] slide_headings = {
            "Opłaty", "Dziecko", "Komunikacja", "Zardządzanie"
    };

    private String[] slide_decriptions = {
            "Brak zaległości", "", "Komunikacja", "Zgłoś", "Do zapłaty: "
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide__layout, container, false);
        if (position == 0) {
            setUpFirstPage(view);
        } else if (position == 1) {
            setUpSecondPage(view);
        } else {
            hideNews(view);
            hideChildInfo(view);
            TextView description = view.findViewById(R.id.desciprtion);
            description.setTextColor(colors[position]);
            GifImageView gif = view.findViewById(R.id.gif);
            gif.setImageResource(gifs[position]);
            description.setText(slide_decriptions[position]);
        }



        container.addView(view);
        return view;
    }

    private void setUpFirstPage(View view) {
        hideChildInfo(view);
        BigDecimal bigDecimal = fetchBalanceStatus();
        TextView description = view.findViewById(R.id.desciprtion);
        GifImageView gif = view.findViewById(R.id.gif);
        if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            description.setTextColor(colors[0]);
            description.setText(slide_decriptions[0]);
            gif.setImageResource(gifs[0]);
        } else {
            description.setTextColor(colors[4]);
            description.setText(slide_decriptions[4] + bigDecimal.toString() + "zł");
            gif.clearAnimation();
            gif.setImageResource(gifs[4]);
        }
        ListView annoucements = view.findViewById(R.id.Annoucments);
        annoucements.setAdapter(new AnnouncmentAdapter(activity, fetchAnnouncement()));
        ListView news = view.findViewById(R.id.news);
        news.setAdapter(new NewsAdapter(activity, fetchNews()));
        LinearLayout annLayout = view.findViewById(R.id.annLayout);
        LinearLayout newsLayout = view.findViewById(R.id.newsLayout);
        annLayout.setVisibility(View.VISIBLE);
        newsLayout.setVisibility(View.VISIBLE);
    }

    private void setUpSecondPage(View view) {
        hideNews(view);
        ChildModel childModel = fetchChild().get(0);
        LinearLayout childTable = view.findViewById(R.id.child_table_layout);
        LinearLayout childButtons = view.findViewById(R.id.child_buttons_layout);
        TextView name = view.findViewById(R.id.name_value);
        name.setText(childModel.getName());
        TextView pesel = view.findViewById(R.id.pesel_value);
        pesel.setText(childModel.getPesel());
        TextView gender = view.findViewById(R.id.gender_value);
        gender.setText(childModel.getGender());
        TextView date = view.findViewById(R.id.date_and_date_birth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(new Date());
        date.setText(formattedDate);
        TextView birthPlace = view.findViewById(R.id.birth_place_value);
        birthPlace.setText(childModel.getBirthPlace());
        childTable.setVisibility(View.VISIBLE);
        childButtons.setVisibility(View.VISIBLE);
        TextView description = view.findViewById(R.id.desciprtion);
        description.setTextColor(colors[1]);
        description.setText(slide_decriptions[1]);
        GifImageView gif = view.findViewById(R.id.gif);
        gif.setImageResource(gifs[1]);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    private void hideNews(View view) {
        LinearLayout annoucements = view.findViewById(R.id.annLayout);
        LinearLayout news = view.findViewById(R.id.newsLayout);
        annoucements.setVisibility(View.GONE);
        news.setVisibility(View.GONE);

    }

    private void hideChildInfo(View view) {
        LinearLayout childTable = view.findViewById(R.id.child_table_layout);
        LinearLayout childButtons = view.findViewById(R.id.child_buttons_layout);
        childButtons.setVisibility(View.GONE);
        childTable.setVisibility(View.GONE);
    }

    private BigDecimal fetchBalanceStatus() {
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");

        String url = "http://ec2-35-180-135-145.eu-west-3.compute.amazonaws.com:8080/tecza/rest/parent/getBalance/1";
        Request request = new Request
                .Builder()
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build();

        Response response = null;
        try {
            response = httpClient
                    .newCall(request)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            try {
                String amount = response.body().string();
                JSONObject jsonObject = new JSONObject(amount);
                String balance = jsonObject.getString("balance");
                return new BigDecimal(balance);
            } catch (JSONException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return BigDecimal.ZERO;
    }

    private List<NewsModel> fetchNews() {
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");

        String url = "http://ec2-35-180-135-145.eu-west-3.compute.amazonaws.com:8080/tecza/rest/parent/getMessages";
        Request request = new Request
                .Builder()
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build();

        Response response = null;
        try {
            response = httpClient
                    .newCall(request)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            try {
                String news = response.body().string();
                JSONArray jsonArray = new JSONArray(news);
                ArrayList<NewsModel> newz = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    NewsModel newsModel = new NewsModel(
                            jsonObject.getLong("id"),
                            jsonObject.getString("content").substring(0, 40) + "...",
                            jsonObject.getString("title"),
                            jsonObject.getString("author"),
                            new Date(jsonObject.getLong("date")));
                    newz.add(newsModel);
                }
                return newz;
            } catch (JSONException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private List<ChildModel> fetchChild() {
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");

        String url = "http://ec2-35-180-135-145.eu-west-3.compute.amazonaws.com:8080/tecza/rest/parent/getAll";
        Request request = new Request
                .Builder()
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build();

        Response response = null;
        try {
            response = httpClient
                    .newCall(request)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            try {
                String news = response.body().string();
                JSONArray jsonArray = new JSONArray(news);
                ArrayList<ChildModel> children = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ChildModel newsModel = new ChildModel(
                            jsonObject.getLong("id"),
                            jsonObject.getString("name") + " " + jsonObject.getString("surname"),
                            jsonObject.getString("pesel"),
                                "chłopiec",
                            "Zamość",
                            new Date()
                            );
                    children.add(newsModel);
                }
                return children;
            } catch (JSONException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private List<NewsModel> fetchAnnouncement() {
        String token = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "");

        String url = "http://ec2-35-180-135-145.eu-west-3.compute.amazonaws.com:8080/tecza/rest/parent/getAnnouncement";
        Request request = new Request
                .Builder()
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build();

        Response response = null;
        try {
            response = httpClient
                    .newCall(request)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            try {
                String news = response.body().string();
                JSONArray jsonArray = new JSONArray(news);
                ArrayList<NewsModel> newz = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String content = jsonObject.getString("content");
                    NewsModel newsModel = new NewsModel(
                            jsonObject.getLong("id"),
                            content.substring(0, Math.min(40, content.length())) + "...",
                            jsonObject.getString("title"),
                            jsonObject.getString("author"),
                            new Date(jsonObject.getLong("date")));
                    newz.add(newsModel);
                }
                return newz;
            } catch (JSONException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }
}
