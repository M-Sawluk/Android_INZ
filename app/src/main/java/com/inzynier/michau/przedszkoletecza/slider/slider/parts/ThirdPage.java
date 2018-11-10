package com.inzynier.michau.przedszkoletecza.slider.slider.parts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.AlergyActivity;
import com.inzynier.michau.przedszkoletecza.ConsultationActivity;
import com.inzynier.michau.przedszkoletecza.EditAbsenceDay;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.TrustedPersonActivity;
import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONException;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ThirdPage extends AbstractPage {

    public ThirdPage(View view, Activity activity, int[] controllsToHide, int[] controllsToShow) {
        super(view, activity, controllsToHide, controllsToShow);
    }

    @Override
    void setUpView() throws JSONException {
        final MaterialCalendarView calendar = view.findViewById(R.id.calendarView);
        calendar.addDecorators(getMarkeDays());
        calendar.setSelectionColor(Color.BLUE);


        calendar.setOnDateLongClickListener((materialCalendarView, calendarDay) -> {
            if (calendarDay.getDate().getDayOfWeek() != DayOfWeek.SATURDAY && calendarDay.getDate().getDayOfWeek() != DayOfWeek.SUNDAY
                    && calendarDay.getDate().isAfter(LocalDate.now().minusDays(1))) {
                Intent editAbsence = new Intent(activity, EditAbsenceDay.class);
                editAbsence.putExtra("day", calendarDay);
                activity.startActivity(editAbsence);
            }
        });

        calendar.setOnDateChangedListener((materialCalendarView, calendarDay, b) -> {
            TextView title = view.findViewById(R.id.absence);
            List<AbsenceDto> absenceRecords = StorageUtils.getAbsenceRecords(activity);
            for (AbsenceDto absenceRecord : absenceRecords) {
                title.setText("Nieobecności");
                if (CalendarDay.from(absenceRecord.getAbsenceDate()).equals(calendarDay)) {
                    title.setText(absenceRecord.getContent());
                    return;
                }
            }
        });

        Button trusted = view.findViewById(R.id.notify_trusted_person);
        trusted.setOnClickListener(v -> {
            Intent intent = new Intent(activity, TrustedPersonActivity.class);
            activity.startActivity(intent);
        });

        Button alergy = view.findViewById(R.id.notify_alergy);

        alergy.setOnClickListener(v -> {
             Intent intent = new Intent(activity, AlergyActivity.class);
             activity.startActivity(intent); }
        );

        Button cons = view.findViewById(R.id.notify_consultation);

        cons.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ConsultationActivity.class);
            activity.startActivity(intent); }
        );
    }


    private List<EventDecorator> getMarkeDays() {
        ArrayList<EventDecorator> decorators = new ArrayList<>();
        List<CalendarDay> absenceDays = ChildInfoFactory
                .mapToCalendarDays(
                        StorageUtils
                                .getAbsenceRecords(activity)
                );
        List<CalendarDay> presenceDays = new ArrayList<>();
        LocalDate start = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);

        while (start.isBefore(LocalDate.now())) {
            if (!absenceDays.contains(CalendarDay.from(start)) && start.getDayOfWeek() != DayOfWeek.SUNDAY && start.getDayOfWeek() != DayOfWeek.SATURDAY) {
                presenceDays.add(CalendarDay.from(start));
            }
            start = start.plusDays(1);
        }

        decorators.add(new EventDecorator(Color.parseColor("#FF0A8730"), presenceDays, 5f));
        decorators.add(new EventDecorator(Color.RED, absenceDays, 12));
        return decorators;
    }
}
