package com.inzynier.michau.przedszkoletecza.forum;

import com.inzynier.michau.przedszkoletecza.consultation.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ForumFactory {

    public static List<Topic> createTopics(String json) {
        try {
            List<Topic> topics = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String creationDate = jsonObject.getString("creationDate");
                long id = jsonObject.getLong("id");
                JSONObject author = jsonObject.getJSONObject("author");
                String name = author.getString("name");
                String surname = author.getString("surname");
                long idAuthor = author.getLong("id");
                int recentlyAddedComments = jsonObject.getInt("recentlyAddedComments");
                topics.add(new Topic(id, title, content, creationDate, new User(idAuthor, name + " " + surname), recentlyAddedComments));
            }
            return topics;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Wrong json");
    }

    public static List<Comments> createComments(String json) {
        try {
            List<Comments> commentz = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray comments = jsonObject.getJSONArray("comments");
            for (int i = 0; i < comments.length(); i++) {
                JSONObject comment = comments.getJSONObject(i);
                String creationDate = comment.getString("creationDate");
                String content = comment.getString("content");
                JSONObject author = comment.getJSONObject("author");

                Comments com = new Comments(author.getString("name") + " " + author.getString("surname"), LocalDate.parse(creationDate), content);

                commentz.add(com);
            }
            return commentz;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Wrong json");
    }

}
