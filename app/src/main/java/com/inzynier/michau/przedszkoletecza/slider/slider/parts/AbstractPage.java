package com.inzynier.michau.przedszkoletecza.slider.slider.parts;

import android.app.Activity;
import android.view.View;

import org.json.JSONException;

public abstract class AbstractPage {
    View view;
    Activity activity;
    int[] controllsToHide;
    int[] controllsToShow;

    public AbstractPage(View view, Activity activity, int[] controllsToHide, int[] controllsToShow) {
        this.view = view;
        this.activity = activity;
        this.controllsToHide = controllsToHide;
        this.controllsToShow = controllsToShow;
    }

    public final void initialize () throws JSONException {
        hideControls();
        setUpView();
        showControls();
    }
    private void hideControls() {
        for (Integer controllsId : controllsToHide) {
            view.findViewById(controllsId).setVisibility(View.GONE);
        }
    }

    private void showControls() {
        for (Integer controllsId : controllsToShow) {
            view.findViewById(controllsId).setVisibility(View.VISIBLE);
        }
    }

    abstract void setUpView() throws JSONException;

}
