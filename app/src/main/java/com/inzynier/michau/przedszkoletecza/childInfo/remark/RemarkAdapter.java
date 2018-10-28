package com.inzynier.michau.przedszkoletecza.childInfo.remark;

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
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.List;

public class RemarkAdapter extends ArrayAdapter<RemakrsDto> {
    private List<RemakrsDto> remarks;
    private Activity activity;

    public RemarkAdapter(@NonNull Activity activity, List<RemakrsDto> news) {
        super(activity, R.layout.remark, news);
        this.remarks = news;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.remark, null);
        TextView author = convertView.findViewById(R.id.remark_author);
        TextView date = convertView.findViewById(R.id.remark_date);
        TextView content = convertView.findViewById(R.id.remark_content);
        LinearLayout row = convertView.findViewById(R.id.remark_row);
        TextView isPositive = convertView.findViewById(R.id.remark_is_positive);
        LocalDate dateLocal = remarks.get(position).getDate();
        author.setText(remarks.get(position).getAuthor());
        content.setText(remarks.get(position).getComment());
        date.setText(dateLocal.toString());

        boolean positive = remarks.get(position).isPositive();
        if(positive){
            isPositive.setText("Pozytywna");
            isPositive.setTextColor(Color.GREEN);
        } else {
            isPositive.setText("Negatywna");
            isPositive.setTextColor(Color.RED);
        }

        if(remarks.get(position).isRead()) {
            row.setBackgroundResource(R.drawable.remark_read);
            row.setEnabled(false);
            row.setOnClickListener(null);
        } else if (remarks.get(position).isPositive()) {
            row.setBackgroundResource(R.drawable.remark_good);
        } else {
            row.setBackgroundResource(R.drawable.remark_bad);
        }

        return convertView;
    }
}