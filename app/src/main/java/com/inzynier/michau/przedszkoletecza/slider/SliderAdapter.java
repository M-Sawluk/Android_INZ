package com.inzynier.michau.przedszkoletecza.slider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.AnnoucementsPage;
import com.inzynier.michau.przedszkoletecza.ChildProgressChart;
import com.inzynier.michau.przedszkoletecza.EditAbsenceDay;
import com.inzynier.michau.przedszkoletecza.NewsPage;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.RemarkPage;
import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;
import com.inzynier.michau.przedszkoletecza.news.adapter.AnnouncmentAdapter;
import com.inzynier.michau.przedszkoletecza.news.adapter.NewsAdapter;
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.AbstractPage;
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.FirstPage;
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.provider.PageFactory;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.readystatesoftware.viewbadger.BadgeView;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class SliderAdapter extends PagerAdapter {
    private Activity activity;
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    public SliderAdapter(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;

    }

    private int[] gifs = {R.drawable.cashok, R.drawable.boy, R.drawable.cashok, R.drawable.cashok, R.drawable.cashwarning};

    private int[] colors = {Color.parseColor("#FF0A8730"), Color.parseColor("#FF0A8730"), Color.parseColor("#FF0A8730"), Color.parseColor("#FF0A8730"), Color.RED};

    private String[] slide_headings = {
            "Opłaty", "Dziecko", "Komunikacja", "Zardządzanie"
    };

    private String[] slide_decriptions = {
            "Brak zaległości", "Twoje Dziecko", "Komunikacja", "Zgłoś", "Do zapłaty: "
    };

    private AbstractPage[] pages;

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
        view = layoutInflater.inflate(R.layout.slide__layout, container, false);
        pages = PageFactory.getPages(view, activity);
        try {
            if (position == 0) {
                pages[0].initialize();
            } else if (position == 1) {
                pages[1].initialize();
            } else {
//                TextView description = view.findViewById(R.id.desciprtion);
//                description.setTextColor(colors[position]);
//                GifImageView gif = view.findViewById(R.id.gif);
//                gif.animate();
//                gif.setImageResource(gifs[position]);
//                description.setText(slide_decriptions[position]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    public void notifyDataSetChanged(int page) {
        DataFetcher dataFetcher = new DataFetcher(activity);
        dataFetcher.fetchAnouncements();
        dataFetcher.fetchChild();
        try {
            List<ChildModel> children = DataFetcher.getChildren(activity);
            dataFetcher.fetchBalanceStatus(children.get(0).getId());
        } catch (JSONException e) {

        }
        dataFetcher.fetchMessages();
    }


}
