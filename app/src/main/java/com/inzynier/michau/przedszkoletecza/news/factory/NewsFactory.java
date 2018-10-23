package com.inzynier.michau.przedszkoletecza.news.factory;

import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsFactory {

    public static List<NewsModel> createTrimmedAnnouncements(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<NewsModel> newz = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String content = jsonObject.getString("content");
            String author = jsonObject.getString("author");
            String title = jsonObject.getString("title");
            String refactoredContent;
            String refactoredAuthor;
            String refactoredTitle;
            if(content.length() > 40) {
                refactoredContent = content.substring(0, 40) + "...";
            } else {
                refactoredContent = content + ("                                       " +
                        "              ").substring(content.length());
            }

            if(author.length() > 10) {
                refactoredAuthor = author.substring(0,9) + "..";
            } else {
                refactoredAuthor = author;
            }

            if(title.length() > 40) {
                refactoredTitle = title.substring(0,40) + "...";
            } else {
                refactoredTitle = title;
            }

            NewsModel newsModel = new NewsModel(
                    jsonObject.getLong("id"),
                    refactoredContent,
                    refactoredTitle,
                    refactoredAuthor,
                    new Date(jsonObject.getLong("date")));
            newz.add(newsModel);
        }
        return newz;
    }

    public static List<NewsModel> createFullAnnouncements(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<NewsModel> newz = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String content = jsonObject.getString("content");
            String author = jsonObject.getString("author");
            String title = jsonObject.getString("title");
            NewsModel newsModel = new NewsModel(
                    jsonObject.getLong("id"),
                    content,
                    title,
                    author,
                    new Date(jsonObject.getLong("date")));
            newz.add(newsModel);
        }
        return newz;
    }

}
