package com.inzynier.michau.przedszkoletecza.slider.slider.parts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.inzynier.michau.przedszkoletecza.utils.PictureUtils;
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONException;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SecondPage extends AbstractPage {
    public static final int GALLERY_PICK = 12121;

    public SecondPage(View view, Activity activity, int[] controllsToHide, int[] controllsToShow) {
        super(view, activity, controllsToHide, controllsToShow);
    }

    @Override
    void setUpView() {
        ChildModel childModel = null;
        try {
            childModel = StorageUtils.getCurrentChilModel(activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        List<RemakrsDto> remarks = StorageUtils.getRemarksList(activity);
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

        long id = StorageUtils.getCurrentChildId(activity);
        Bitmap pictureForChild = PictureUtils.getPictureForChild(id, activity);
        circleImage.setImageBitmap(pictureForChild);

        name.setText(childModel.getName());
        pesel.setText(childModel.getPesel());
        gender.setText(childModel.getGender());
        date.setText(childModel.getDate());
    }






}
