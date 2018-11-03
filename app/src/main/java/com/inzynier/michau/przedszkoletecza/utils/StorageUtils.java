package com.inzynier.michau.przedszkoletecza.utils;

import android.app.Activity;
import android.content.Context;

import com.inzynier.michau.przedszkoletecza.childInfo.AbsenceDto;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildInfoFactory;
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel;
import com.inzynier.michau.przedszkoletecza.childInfo.progress.ChildProgressDto;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.inzynier.michau.przedszkoletecza.news.factory.NewsFactory;
import com.inzynier.michau.przedszkoletecza.news.model.NewsModel;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

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

    public static List<ChildProgressDto> getProgressList(Activity activity) {
        return ChildInfoFactory
                .createProgressGrades(
                        activity
                                .getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("progressEvaluation", "")
                );
    }

    public static void setCurrentChild(Activity activity,long id) {
        activity.getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                .edit()
                .putLong("current_child_id", id)
                .apply();

    }

    public static long getCurrentChild(Activity activity) {
        return activity.getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getLong("current_child_id", 0L);

    }
}
