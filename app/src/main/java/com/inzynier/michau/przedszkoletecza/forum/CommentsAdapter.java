package com.inzynier.michau.przedszkoletecza.forum;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.childInfo.progress.ChildProgressDto;

import java.util.List;

public class CommentsAdapter extends ArrayAdapter<Comments> {
    private List<Comments> comments;
    private Activity activity;

    public CommentsAdapter(@NonNull Activity activity, List<Comments> comments) {
        super(activity, R.layout.comment_item, comments);
        this.comments = comments;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Comments comment = comments.get(position);
        convertView = layoutInflater.inflate(R.layout.comment_item, null);

        TextView content = convertView.findViewById(R.id.user_comment);
        TextView author = convertView.findViewById(R.id.comment_autor);
        TextView date = convertView.findViewById(R.id.comment_date);
        content.setText(comment.getContent());
        author.setText(comment.getFullName());
        date.setText(comment.getCreationDate().toString());

        return convertView;
    }
}
