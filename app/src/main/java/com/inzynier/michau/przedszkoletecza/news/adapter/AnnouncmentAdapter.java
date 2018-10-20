package com.inzynier.michau.przedszkoletecza.news.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnnouncmentAdapter extends ArrayAdapter<NewsModel> {
    private List<NewsModel> news;
    private Activity activity;

    public AnnouncmentAdapter(@NonNull Activity activity, List<NewsModel> news) {
        super(activity, R.layout.annoucement_s, news);
        this.news = news;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.annoucement_s, null);

//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity.getApplicationContext(), AddActivity.class);
//                intent.putExtra("phone", phones.get(position));
//                activity.startActivity(intent);
//            }
//        });

        TextView author = convertView.findViewById(R.id.author);
        TextView date = convertView.findViewById(R.id.date);
        TextView title = convertView.findViewById(R.id.title);
        TextView content = convertView.findViewById(R.id.content);
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