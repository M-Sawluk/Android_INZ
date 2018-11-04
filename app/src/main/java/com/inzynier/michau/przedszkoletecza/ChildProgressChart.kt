package com.inzynier.michau.przedszkoletecza

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_child_progress_chart.*

class ChildProgressChart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_progress_chart)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        phisic_id
                .setOnClickListener {
                    val intent = Intent(this, PhisicalProgress::class.java)

                    val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(phisic_id,
                            "phisic_transition"))
                    startActivity(intent, options.toBundle())
                }
        mind_id
                .setOnClickListener {
                    val intent = Intent(this, MentalProgress::class.java)
                    val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(mind_id,
                            "mind_transition"))
                    startActivity(intent, options.toBundle())
                }
        social_id
                .setOnClickListener {
                    val intent = Intent(this, SocialProgress::class.java)
                    val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(social_id,
                            "social_transition"))
                    startActivity(intent, options.toBundle())
                }
        additional_id
                .setOnClickListener {
                    val intent = Intent(this, AdditionalAchievements::class.java)
                    val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(additional_id,
                            "additional_transition"))
                    startActivity(intent, options.toBundle())
                }


    }
}
