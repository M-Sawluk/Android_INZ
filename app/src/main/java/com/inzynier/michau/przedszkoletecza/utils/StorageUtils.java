package com.inzynier.michau.przedszkoletecza.utils;

import android.app.Activity;
import android.content.Context;

import com.inzynier.michau.przedszkoletecza.TrustedPersonActivity;
import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.childInfo.progress.ChildProgressDto;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.inzynier.michau.przedszkoletecza.childInfo.trusted.ppl.TrustedPersonModel;
import com.inzynier.michau.przedszkoletecza.consultation.ConsultationFactory;
import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationModel;
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher;
import com.inzynier.michau.przedszkoletecza.forum.Comments;
import com.inzynier.michau.przedszkoletecza.forum.ForumFactory;
import com.inzynier.michau.przedszkoletecza.forum.Topic;
import com.inzynier.michau.przedszkoletecza.news.factory.NewsFactory;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageUtils {
    public static BigDecimal getBalance(Activity activity) {
        return new BigDecimal(
                activity
                        .getSharedPreferences("data", Context.MODE_PRIVATE)
                        .getString("balance", "-1")
        );
    }

    public static List<ChildModel> getChildren(Activity activity) throws JSONException {
        return ChildInfoFactory
                .createChildren(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("children", "")
                );
    }

    public static List<NewsModel> getTrimmedNews(Activity activity) throws JSONException {
        return NewsFactory
                .createTrimmedAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("messages", "")
                );
    }

    public static List<NewsModel> getTrimmmedAnnouncement(Activity activity) throws JSONException {
        return NewsFactory
                .createTrimmedAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("announcement", "")
                );
    }

    public static List<NewsModel> getFullNews(Activity activity) throws JSONException {
        return NewsFactory
                .createFullAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("messages", "")
                );
    }

    public static List<NewsModel> getFullAnnouncement(Activity activity) throws JSONException {
        return NewsFactory
                .createFullAnnouncements(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("announcement", "")
                );
    }

    public static List<AbsenceDto> getAbsenceRecords(Activity activity) {
        return ChildInfoFactory
                .createAbsences(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("absenceRecords", "")
                );
    }

    public static List<RemakrsDto> getRemarksList(Activity activity) {
        return ChildInfoFactory
                .createRemarks(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("childRemarks", "")
                );
    }

    public static Map<String, List<ChildProgressDto>> getProgressList(Activity activity) {
        return ChildInfoFactory
                .createProgressGradesMap(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("progressEvaluation", "")
                );
    }

    public static void setCurrentChildId(Activity activity, long id) {
        activity.getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                .edit()
                .putLong("current_child_id", id)
                .apply();

    }

    public static long getCurrentChildId(Activity activity) {
        return activity.getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getLong("current_child_id", 0L);

    }

    public static ChildModel getCurrentChilModel(Activity activity) throws JSONException {
        long current_child_id = activity.getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getLong("current_child_id", 0L);
        ChildModel childModel = null;

        for (ChildModel model : getChildren(activity)) {
            if (model.getId() == current_child_id)
                childModel = model;
        }

        return childModel;

    }

    public static List<TrustedPersonModel> getTrustedPpl(Activity activity) throws JSONException {
        return ChildInfoFactory.getTrustedPpl(activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                .getString("trustedppl", ""));

    }

    public static List<ConsultationModel> getConsultations(Activity activity) {
        DataFetcher dataFetcher = new DataFetcher(activity);
        dataFetcher.fetchConsultationData();
        return ConsultationFactory.create(activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                .getString("consultations", ""));

    }

    public static List<Topic> getTopics(Activity activity) {
        DataFetcher dataFetcher = new DataFetcher(activity);
        dataFetcher.fetchForumData();

        return ForumFactory.createTopics(
                activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                        .getString("forum", "")
        );
    }

    public static List<Comments> getComments(Activity activity, long id) {
        DataFetcher dataFetcher = new DataFetcher(activity);
        dataFetcher.fetchComments(id);

        return ForumFactory.createComments(
                activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                        .getString("comments", "")
        );
    }
}
