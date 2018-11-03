package com.inzynier.michau.przedszkoletecza.context;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ActivityOptions;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.LoginPage;
import com.inzynier.michau.przedszkoletecza.MiddleSwitchContext;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.utils.Consts;
import com.inzynier.michau.przedszkoletecza.utils.PictureUtils;

import java.util.List;

public class ContextAdapter extends RecyclerView.Adapter<ContextViewHolder> {
    private Activity activity;
    private List<ChildModel> childModels;
    private int itemCount ;

    public ContextAdapter(Activity activity, List<ChildModel> childModel) {
        this.activity = activity;
        this.childModels = childModel;
        itemCount = childModel.size() + 1;
    }


    @Override
    public ContextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.context_item, parent, false);
        return new ContextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContextViewHolder holder, final int position) {
        if(childModels.size() > position) {
            ChildModel childModel = childModels.get(position);
            holder.name.setText(childModel.getName());
            holder.image.setImageBitmap(PictureUtils.getPictureForChild(childModel.getId(), activity));
            holder.image.setOnClickListener( v -> {
                TextView chooseChild = activity.findViewById(R.id.chose_child);
                Intent intent = new Intent(activity, MiddleSwitchContext.class);
                intent.putExtra("child", childModel);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        activity,
                        Pair.create(chooseChild, "context_title_transition"));
                activity.startActivity(intent, activityOptions.toBundle());
            });
        } else {
            holder.name.setText("Wyloguj się");
            holder.image.setBackgroundResource(R.drawable.logout);
            holder.image.setOnClickListener(v ->  {
                activity.getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                        .edit()
                        .putString(Consts.TOKEN, "")
                        .apply();
                activity.startActivity(new Intent(activity, LoginPage.class));
            });

        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

}