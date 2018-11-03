package com.inzynier.michau.przedszkoletecza.slider.slider.parts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.ChildProgressChart;
import com.inzynier.michau.przedszkoletecza.EditAbsenceDay;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.RemarkPage;
import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONException;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class SecondPage extends AbstractPage {
    public static final int GALLERY_PICK = 12121;

    public SecondPage(View view, Activity activity, int[] controllsToHide, int[] controllsToShow) {
        super(view, activity, controllsToHide, controllsToShow);
    }

    @Override
    void setUpView() throws JSONException {
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
            if (calendarDay.getDate().getDayOfWeek() != DayOfWeek.SATURDAY && calendarDay.getDate().getDayOfWeek() != DayOfWeek.SUNDAY
                    && calendarDay.getDate().isAfter(LocalDate.now().minusDays(1))) {
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
                if (CalendarDay.from(absenceRecord.getAbsenceDate()).equals(calendarDay)) {
                    title.setText(absenceRecord.getContent());
                    return;
                }
            }
        });
        LinearLayout childButtonz = view.findViewById(R.id.child_buttons);
        TextView name = view.findViewById(R.id.name_value);
        TextView pesel = view.findViewById(R.id.pesel_value);
        TextView gender = view.findViewById(R.id.gender_value);
        TextView date = view.findViewById(R.id.date_and_date_birth);
        Button remarks_but = view.findViewById(R.id.remark_button);
        Button child_progress = view.findViewById(R.id.child_progress);
        remarks_but.setOnClickListener(v -> {
            Intent intent = new Intent(activity, RemarkPage.class);
            activity.startActivity(intent);
        });
        CircleImageView circleImage = view.findViewById(R.id.child_image);
        List<RemakrsDto> remarks = DataFetcher.getRemarksList(activity);
        String newRemarksCount = ChildInfoFactory.getNewRemarksCount(remarks);
        BadgeView badgeView = new BadgeView(activity, childButtonz);
        if (!"0".equals(newRemarksCount)) {
            badgeView.setText(newRemarksCount);
            badgeView.show();
        }
        child_progress.setOnClickListener(
                v -> {
                    Intent intent = new Intent(activity, ChildProgressChart.class);
                    activity.startActivity(intent);
                }
        );
        circleImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            activity.startActivityForResult(intent, GALLERY_PICK);
        });

        long id = DataFetcher.getChildren(activity).get(0).getId();

        final File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/saved_images/child_pic_" + id + ".png");

        if(file.exists()) {
            try {
                RandomAccessFile r = new RandomAccessFile(file, "r");
                byte[] b = new byte[(int)r.length()];
                r.readFully(b);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                circleImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        name.setText(childModel.getName());
        pesel.setText(childModel.getPesel());
        gender.setText(childModel.getGender());
        date.setText(childModel.getDate());
    }





    private List<EventDecorator> getMarkeDays() {
        ArrayList<EventDecorator> decorators = new ArrayList<>();
        List<CalendarDay> absenceDays = ChildInfoFactory
                .mapToCalendarDays(
                        DataFetcher
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

        decorators.add(new EventDecorator(Color.GREEN, presenceDays, 5f));
        decorators.add(new EventDecorator(Color.RED, absenceDays, 12));
        return decorators;
    }
}
