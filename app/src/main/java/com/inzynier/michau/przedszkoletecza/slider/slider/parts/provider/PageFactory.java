package com.inzynier.michau.przedszkoletecza.slider.slider.parts.provider;

import android.app.Activity;
import android.view.View;

import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.AbstractPage;
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.FirstPage;
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.SecondPage;

public class PageFactory {

    public static AbstractPage[] getPages(View view, Activity activity) {
        return new AbstractPage[] {
                new FirstPage(
                        view,
                        activity,
                        new int [] {R.id.child_table_layout ,R.id.child_buttons_layout, R.id.child_buttons},
                        new int [] {R.id.desciprtion, R.id.annLayout, R.id.newsLayout, R.id.Annoucments}),
                new SecondPage(
                        view,
                        activity,
                        new int[] {R.id.annLayout, R.id.newsLayout, R.id.gif},
                        new int[] {R.id.child_table_layout, R.id.child_buttons_layout, R.id.child_buttons, R.id.child_image})

        };




    }
}
