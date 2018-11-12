package com.inzynier.michau.przedszkoletecza.forum;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inzynier.michau.przedszkoletecza.AddComment;
import com.inzynier.michau.przedszkoletecza.AddTopic;
import com.inzynier.michau.przedszkoletecza.MainPage;
import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.childInfo.progress.ChildProgressDto;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;
import com.inzynier.michau.przedszkoletecza.data.fetcher.RequestHelper;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

import java.io.Serializable;
import java.util.List;

import okhttp3.Response;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.TopicHolder> {
    private List<Topic> topics;
    private Activity activity;

    public ForumAdapter(@NonNull Activity activity, List<Topic> topics) {
        this.topics = topics;
        this.activity = activity;
    }

    @Override
    public TopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forum_item, parent, false);

        return new TopicHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder topicHolder, int position) {
        Topic topic = topics.get(position);
        topicHolder.forumAuthor.setText(topicHolder.forumAuthor.getText() + topic.getAuthor().getFullname());
        topicHolder.forumTitle.setText(topicHolder.forumTitle.getText() + topic.getTitle());
        topicHolder.forumNewComments.setText(topicHolder.forumNewComments.getText() + ": " + String.valueOf(topic.getRecentlyAddedComments()) + " ");
        topicHolder.forumData.setText(topicHolder.forumData.getText() + topic.getCreationDate());
        topicHolder.cardView.setBackgroundResource(R.drawable.post4);
        topicHolder.topicId.setText(String.valueOf(topic.getId()));
        int row = position / 2;
        if (row % 2 == 0) {
            if (position % 2 == 0) {
                topicHolder.cardView.setCardBackgroundColor(Color.parseColor("#E24E42"));
            }
        } else {
            if (position % 2 != 0) {
                topicHolder.cardView.setCardBackgroundColor(Color.parseColor("#E24E42"));
            }
        }

        topicHolder.cardView.setOnLongClickListener(v -> {
            TransitionManager.beginDelayedTransition(topicHolder.cardView,new Slide(Gravity.BOTTOM));
            topicHolder.linearLayout.setVisibility(View.VISIBLE);
            return true;
        });
        topicHolder.cardView.setOnClickListener(v-> {
            TransitionManager.beginDelayedTransition(topicHolder.cardView, new Slide(Gravity.BOTTOM));
            topicHolder.linearLayout.setVisibility(View.GONE);
            Intent intent = new Intent(activity, AddComment.class);
            intent.putExtra("topic", topic);
            ActivityOptions trans = ActivityOptions.makeSceneTransitionAnimation(activity, Pair.create(topicHolder.cardView, "forumTopicTrans"));
            activity.startActivity(intent, trans.toBundle());
        });

        topicHolder.delete.setOnClickListener( v-> {
            Response response = RequestHelper.makeRequest("news/deleteTopic/" + topicHolder.topicId.getText(), activity);
            if (response.isSuccessful()) {
                DataFetcher dataFetcher = new DataFetcher(activity);
                dataFetcher.fetchForumData();
                Toast dodano_nowy_wątek = Toast.makeText(activity, "Usunięto wątek: " + topicHolder.forumTitle.getText(), Toast.LENGTH_SHORT);
                dodano_nowy_wątek.show();
                Intent intent = new Intent(activity, MainPage.class);
                intent.putExtra("page", 3);
                activity.startActivity(intent);
            } else {
                Toast dodano_nowy_wątek = Toast.makeText(activity, "Nie jesteś autorem tego posta, nie możesz go usunąć", Toast.LENGTH_SHORT);
                dodano_nowy_wątek.show();
            }
        });

        topicHolder.edit.setOnClickListener( v -> {
            Intent intent = new Intent(activity, AddTopic.class);
            intent.putExtra("topic", topic);
            activity.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class TopicHolder extends RecyclerView.ViewHolder implements Serializable {
        public TextView forumAuthor, forumTitle, forumNewComments, forumData, topicId;
        public CardView cardView;
        public LinearLayout linearLayout;
        public ImageView edit, delete;
        public TopicHolder(View view) {
            super(view);
            forumAuthor = view.findViewById(R.id.forumAuthor);
            forumTitle = view.findViewById(R.id.forumTitle);
            forumNewComments = view.findViewById(R.id.forumNewComments);
            forumData = view.findViewById(R.id.forumData);
            cardView = view.findViewById(R.id.forumCard);
            linearLayout = view.findViewById(R.id.editDeletContainer);
            topicId = view.findViewById(R.id.topicid);
            edit = view.findViewById(R.id.editTopic);
            delete = view.findViewById(R.id.deleteTopic);
        }
    }
}
