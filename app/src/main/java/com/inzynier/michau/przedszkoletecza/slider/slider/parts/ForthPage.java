package com.inzynier.michau.przedszkoletecza.slider.slider.parts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.inzynier.michau.przedszkoletecza.AddTopic;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.forum.ForumAdapter;
import com.inzynier.michau.przedszkoletecza.forum.Topic;
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils;

import java.util.List;

public class ForthPage extends AbstractPage {


    public ForthPage(View view, Activity activity, int[] controllsToHide, int[] controllsToShow) {
        super(view, activity, controllsToHide, controllsToShow);
    }

    @Override
    void setUpView() {
        RecyclerView forum = view.findViewById(R.id.forumContainer);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        forum.setLayoutManager(gridLayoutManager);
        List<Topic> topics = StorageUtils.getTopics(activity);
        forum.setAdapter(new ForumAdapter(activity, topics));

        ImageView addTopic = view.findViewById(R.id.addTopic);
        addTopic.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, AddTopic.class)));

    }
}
