package com.inzynier.michau.przedszkoletecza.news.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsModel> {
    private List<NewsModel> news;
    private Activity activity;

    public NewsAdapter(@NonNull Activity activity, List<NewsModel> news) {
        super(activity, R.layout.news_layout, news);
        this.news = news;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.news_layout, null);
        TextView author = convertView.findViewById(R.id.news_author);
        TextView date = convertView.findViewById(R.id.news_date);
        TextView title = convertView.findViewById(R.id.news_title);
        TextView content = convertView.findViewById(R.id.news_content);
        Date dateToDisplayed = news.get(position).getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(dateToDisplayed);
        author.setText(news.get(position).getAuthor());
        date.setText(formattedDate);
        title.setText(news.get(position).getTitle());
        content.setText(news.get(position).getDescription());
        return convertView;
    }
}