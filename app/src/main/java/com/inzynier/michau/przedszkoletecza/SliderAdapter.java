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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.news.adapter.AnnouncmentAdapter;
import com.inzynier.michau.przedszkoletecza.news.adapter.NewsAdapter;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class SliderAdapter extends PagerAdapter {
    Activity activity;
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
    }

    private int[] gifs = {
            R.drawable.cashok, R.drawable.cashwarning, R.drawable.cashwarning, R.drawable.cashwarning
    };

    private int[] colors = {
            Color.parseColor("#FF0A8730"), Color.BLACK, Color.BLACK, Color.BLACK
    };

    private String[] slide_headings = {
            "Opłaty", "Dziecko", "Komunikacja", "Zardządzanie"
    };

    private String[] slide_decriptions = {
            "Brak zaległości", "Tomasz \n 5 lat",
            "Nowych postów 5 \n Online: Marta Kosior",
            "Zarządzaj akcjami"
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
        } else {
            LinearLayout annoucements = view.findViewById(R.id.annLayout);
            LinearLayout news = view.findViewById(R.id.newsLayout);
            annoucements.setVisibility(View.GONE);
            news.setVisibility(View.GONE);
        }
        TextView description = view.findViewById(R.id.desciprtion);
        description.setTextColor(colors[position]);
        GifImageView gif = view.findViewById(R.id.gif);
        gif.setImageResource(gifs[position]);
        description.setText(slide_decriptions[position]);


        container.addView(view);
        return view;
    }


    private void setUpFirstPage(View view) {
        TextView description = view.findViewById(R.id.desciprtion);
        description.setTextColor(colors[0]);
        description.setText(slide_decriptions[0]);
        GifImageView gif = view.findViewById(R.id.gif);
        gif.setImageResource(gifs[0]);
        ListView annoucements = view.findViewById(R.id.Annoucments);
        List<NewsModel> announcementModels = Arrays.asList(new NewsModel(1L, "desc11111111111111111111111111", "title1", "author1", new Date()), new NewsModel(2L, "desc2", "title2", "author2", new Date()),
                new NewsModel(3L, "desc3", "title3", "author3", new Date()));
        annoucements.setAdapter(new AnnouncmentAdapter(activity, announcementModels));
        ListView news = view.findViewById(R.id.news);
        List<NewsModel> newsModels = Arrays.asList(new NewsModel(1L, "Zawartość wiadomości 1", "Tytul 1", "Pani Zosia", new Date()), new NewsModel(2L, "Zawartość wiadomości 2", "Tytul 2", "Pani Małgosia", new Date()),
                new NewsModel(3L, "Zawartość wiadomości 3", "Tytul 3", "Dyrektor", new Date()));
        news.setAdapter(new NewsAdapter(activity, newsModels));
        LinearLayout annLayout = view.findViewById(R.id.annLayout);
        LinearLayout newsLayout = view.findViewById(R.id.newsLayout);
        annLayout.setVisibility(View.VISIBLE);
        newsLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
