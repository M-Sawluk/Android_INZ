package com.inzynier.michau.przedszkoletecza

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

class AdditionalAchievements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_achievements)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
}
