package com.inzynier.michau.przedszkoletecza.slider.slider.parts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.AnnoucementsPage;
import com.inzynier.michau.przedszkoletecza.NewsPage;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;
import com.inzynier.michau.przedszkoletecza.news.adapter.AnnouncmentAdapter;
import com.inzynier.michau.przedszkoletecza.news.adapter.NewsAdapter;
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class FirstPage extends AbstractPage {
    private static int[] gifs = {R.drawable.cashok, R.drawable.cashwarning};

    private static int[] colors = {Color.parseColor("#FF0A8730"), Color.RED};

    private static String[] slide_decriptions = {"Brak zaległości", "Do zapłaty: "};

    public FirstPage(View view, Activity activity, int[] controllsToHide, int[] controllsToShow) {
        super(view, activity, controllsToHide, controllsToShow);
    }

    @Override
    public void setUpView() throws JSONException {
        BigDecimal bigDecimal = StorageUtils.getBalance(activity);
        TextView description = view.findViewById(R.id.desciprtion);
        LinearLayout annLayout = view.findViewById(R.id.annLayout);
        LinearLayout newsLayout = view.findViewById(R.id.newsLayout);
        ListView annoucements = view.findViewById(R.id.Annoucments);
        ListView news = view.findViewById(R.id.news);
        GifImageView gif = view.findViewById(R.id.gif);
        news.setAdapter(new NewsAdapter(activity, StorageUtils.getTrimmedNews(activity)));
        annoucements.setAdapter(new AnnouncmentAdapter(activity, StorageUtils.getTrimmmedAnnouncement(activity)));

        if (bigDecimal.compareTo(BigDecimal.ZERO) >= 0) {
            description.setTextColor(colors[0]);
            description.setText(slide_decriptions[0]);
            gif.setImageResource(gifs[0]);
            gif.animate();
        } else {
            description.setTextColor(colors[1]);
            description.setText(slide_decriptions[1] + bigDecimal.toString() + "zł");
            description.refreshDrawableState();
            gif.animate();
            gif.setImageResource(gifs[1]);
        }

        annLayout.setVisibility(View.VISIBLE);
        newsLayout.setVisibility(View.VISIBLE);
        gif.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);

        annoucements.setOnItemClickListener(
                getAnnoucementsListener()
        );

        news.setOnItemClickListener(
                getNewsListener()
        );
    }

    private AdapterView.OnItemClickListener getAnnoucementsListener() {
        return (parent, view, position, id) -> {
            Intent intent = new Intent(activity, AnnoucementsPage.class);
            intent.putExtra("ann_id", position);
            activity.startActivity(intent);
        };
    }

    private AdapterView.OnItemClickListener getNewsListener() {
        return (parent, view, position, id) -> {
            Intent intent = new Intent(activity, NewsPage.class);
            intent.putExtra("news_id", position);
            activity.startActivity(intent);
        };
    }
}
