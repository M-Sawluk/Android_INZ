package com.inzynier.michau.przedszkoletecza.slider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.AnnoucementsPage;
import com.inzynier.michau.przedszkoletecza.EditAbsenceDay;
import com.inzynier.michau.przedszkoletecza.NewsPage;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;
import com.inzynier.michau.przedszkoletecza.news.adapter.AnnouncmentAdapter;
import com.inzynier.michau.przedszkoletecza.news.adapter.NewsAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONException;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class SliderAdapter extends PagerAdapter {
    private Activity activity;
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private ViewGroup viewGroup;

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
        this.viewGroup = container;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.slide__layout, container, false);
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
            gif.animate();
            gif.setImageResource(gifs[position]);
            description.setText(slide_decriptions[position]);
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    private void setUpFirstPage(View view) {
        hideChildInfo(view);
        BigDecimal bigDecimal = DataFetcher.getBalance(activity);
        TextView description = view.findViewById(R.id.desciprtion);
        LinearLayout annLayout = view.findViewById(R.id.annLayout);
        LinearLayout newsLayout = view.findViewById(R.id.newsLayout);
        ListView annoucements = view.findViewById(R.id.Annoucments);
        ListView news = view.findViewById(R.id.news);
        GifImageView gif = view.findViewById(R.id.gif);
        try {
            news.setAdapter(new NewsAdapter(activity, DataFetcher.getTrimmedNews(activity)));
            annoucements.setAdapter(new AnnouncmentAdapter(activity, DataFetcher.getTrimmmedAnnouncement(activity)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bigDecimal.compareTo(BigDecimal.ZERO) >= 0) {
            description.setTextColor(colors[0]);
            description.setText(slide_decriptions[0]);
            gif.setImageResource(gifs[0]);
            gif.animate();
        } else {
            description.setTextColor(colors[4]);
            description.setText(slide_decriptions[4] + bigDecimal.toString() + "zł");
            description.refreshDrawableState();
            gif.animate();
            gif.setImageResource(gifs[4]);
        }

        annLayout.setVisibility(View.VISIBLE);
        newsLayout.setVisibility(View.VISIBLE);
        gif.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);

        annoucements.setOnItemClickListener(
                (parent, view1, position, id) -> {
                    Intent intent = new Intent(activity, AnnoucementsPage.class);
                    intent.putExtra("ann_id", position);
                    activity.startActivity(intent);
                }
        );

        news.setOnItemClickListener((parent, view12, position, id) -> {
            Intent intent = new Intent(activity, NewsPage.class);
            intent.putExtra("news_id", position);
            activity.startActivity(intent);
        });
    }

    private void setUpSecondPage(final View view) {
        hideNews(view);
        ChildModel childModel = null;
        try {
            childModel = DataFetcher.getChildren(activity).get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MaterialCalendarView calendar = view.findViewById(R.id.calendarView);
        calendar.addDecorators(getMarkeDays());
        calendar.setSelectionColor(Color.BLUE);


        calendar.setOnDateLongClickListener((materialCalendarView, calendarDay) -> {
            if(calendarDay.getDay() != 6 && calendarDay.getDay() !=7 && calendarDay.getDate().isAfter(LocalDate.now().minusDays(1))) {
                Intent editAbsence = new Intent(activity, EditAbsenceDay.class);
                editAbsence.putExtra("day", calendarDay);
                activity.startActivity(editAbsence);
            }
        });

        calendar.setOnDateChangedListener((materialCalendarView, calendarDay, b) -> {
            TextView title = view.findViewById(R.id.absence);
            List<AbsenceDto> absenceRecords = DataFetcher.getAbsenceRecords(activity);
            for (AbsenceDto absenceRecord : absenceRecords) {
                title.setText("Nieobecności");
                if(CalendarDay.from(absenceRecord.getAbsenceDate()).equals(calendarDay)) {
                    title.setText(absenceRecord.getContent());
                    return;
                }
            }
        });


        LinearLayout childTable = view.findViewById(R.id.child_table_layout);
        LinearLayout childButtons = view.findViewById(R.id.child_buttons_layout);
        LinearLayout childButtonz = view.findViewById(R.id.child_buttons);
        TextView name = view.findViewById(R.id.name_value);
        TextView pesel = view.findViewById(R.id.pesel_value);
        TextView gender = view.findViewById(R.id.gender_value);
        TextView date = view.findViewById(R.id.date_and_date_birth);
        TextView description = view.findViewById(R.id.desciprtion);
        GifImageView gif = view.findViewById(R.id.gif);
        name.setText(childModel.getName());
        pesel.setText(childModel.getPesel());
        gender.setText(childModel.getGender());
        date.setText(childModel.getDate());
        description.setTextColor(colors[1]);
        description.setText(slide_decriptions[1]);
        gif.setImageResource(childModel.getPicture());
        childButtons.setVisibility(View.VISIBLE);
        childTable.setVisibility(View.VISIBLE);
        gif.setVisibility(View.VISIBLE);
        childButtonz.setVisibility(View.VISIBLE);
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
        LinearLayout childButtonz = view.findViewById(R.id.child_buttons);
        childButtonz.setVisibility(View.GONE);
        childButtons.setVisibility(View.GONE);
        childTable.setVisibility(View.GONE);
    }

    public void notifyDataSetChanged(int page) {
        DataFetcher dataFetcher = new DataFetcher(activity);
        dataFetcher.fetchAnouncements();
        dataFetcher.fetchBalanceStatus();
        dataFetcher.fetchChild();
        dataFetcher.fetchMessages();
        destroyItem(viewGroup, page, view);
        instantiateItem(viewGroup, page);
    }

    private List<EventDecorator> getMarkeDays() {
        ArrayList<EventDecorator> decorators = new ArrayList<>();
        List<CalendarDay> absenceDays = ChildInfoFactory
                .mapToCalendarDays(
                        DataFetcher
                                .getAbsenceRecords(activity)
                );
        List<CalendarDay> presenceDays = new ArrayList<>();
        LocalDate start = LocalDate.ofYearDay(2018, 1);

        while (start.isBefore(LocalDate.now())) {
            if(!absenceDays.contains(CalendarDay.from(start)) && start.getDayOfWeek() != DayOfWeek.SUNDAY && start.getDayOfWeek() != DayOfWeek.SATURDAY) {
                presenceDays.add(CalendarDay.from(start));
            }
            start = start.plusDays(1);
        }

        decorators.add(new EventDecorator(Color.GREEN, presenceDays, 5f));
        decorators.add(new EventDecorator(Color.RED, absenceDays, 12));
        return decorators;
    }
}
