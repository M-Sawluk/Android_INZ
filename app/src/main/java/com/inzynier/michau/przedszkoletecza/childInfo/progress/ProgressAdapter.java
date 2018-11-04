package com.inzynier.michau.przedszkoletecza.childInfo.progress;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.R;

import org.w3c.dom.Text;

import java.util.List;

public class ProgressAdapter extends ArrayAdapter<ChildProgressDto> {
    private List<ChildProgressDto> remarks;
    private Activity activity;

    public ProgressAdapter(@NonNull Activity activity, List<ChildProgressDto> progressDtos) {
        super(activity, R.layout.remark, progressDtos);
        this.remarks = progressDtos;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ChildProgressDto childProgressDto = remarks.get(position);
        convertView = layoutInflater.inflate(R.layout.task_item, null);
        String tak = " Czasami Nie";
        String nie = " Czasami Tak";
        String czasami = " Nie Tak";
        TextView task = convertView.findViewById(R.id.taskkk);
        TextView grade = convertView.findViewById(R.id.gradddde);
        TextView otherGrade = convertView.findViewById(R.id.other_grade);
        CardView card = convertView.findViewById(R.id.grade_card);

        task.setText(childProgressDto.getTask());
        String childGrade = childProgressDto.getGrade();
        if (childGrade.equalsIgnoreCase("tak")) {
            card.setCardBackgroundColor(Color.parseColor("#008F95"));
            grade.setText(" " +childGrade);
            otherGrade.setText(tak);
        } else if (childGrade.equalsIgnoreCase("nie")) {
            card.setCardBackgroundColor(Color.parseColor("#E24E42"));
            grade.setText(" " +childGrade);
            otherGrade.setText(nie);
        } else {
            card.setCardBackgroundColor(Color.parseColor("#EB6E80"));
            grade.setText(" " +childGrade);
            otherGrade.setText(czasami);
        }


        return convertView;
    }
}
