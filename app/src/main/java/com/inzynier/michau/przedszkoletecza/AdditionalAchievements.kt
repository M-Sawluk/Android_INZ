package com.inzynier.michau.przedszkoletecza

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.childInfo.progress.ProgressAdapter
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import kotlinx.android.synthetic.main.activity_phisical_progress.*

class AdditionalAchievements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_achievements)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
}
