package com.inzynier.michau.przedszkoletecza.childInfo.progress;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;

import org.threeten.bp.LocalDate;

import java.util.List;

public class ProgressAdapter extends ArrayAdapter<ChildProgressDto> {
    private List<ChildProgressDto> remarks;
    private Activity activity;

    public ProgressAdapter(@NonNull Activity activity, List<ChildProgressDto> news) {
        super(activity, R.layout.remark, news);
        this.remarks = news;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ChildProgressDto childProgressDto = remarks.get(position);
        if(childProgressDto.getGrade().isEmpty()) {
            convertView = layoutInflater.inflate(R.layout.progress_category, null);
            TextView category = convertView.findViewById(R.id.progress_category);
            category.setText(childProgressDto.getCategory());
        } else {
            convertView = layoutInflater.inflate(R.layout.progress_grade, null);
            TextView task = convertView.findViewById(R.id.progress_task);
            TextView grade = convertView.findViewById(R.id.progress_grade);
            LinearLayout lay = convertView.findViewById(R.id.progress_mark_lay);
            task.setText(childProgressDto.getTask());
            grade.setText(childProgressDto.getGrade());
            if(childProgressDto.getGrade().equals("Nie")) {
                lay.setBackgroundResource(R.drawable.remark_bad);
            } else if (childProgressDto.getGrade().equals("Tak")) {
                lay.setBackgroundResource(R.drawable.remark_good);
            } else {
                lay.setBackgroundResource(R.drawable.remark_yello);
            }
        }
        return convertView;
    }
}
