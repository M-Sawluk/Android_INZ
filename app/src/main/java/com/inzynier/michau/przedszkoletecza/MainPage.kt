package com.inzynier.michau.przedszkoletecza

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.slider.SliderAdapter
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.SecondPage
import com.inzynier.michau.przedszkoletecza.utils.Consts
import com.inzynier.michau.przedszkoletecza.utils.PictureUtils
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.transitionseverywhere.Rotate
import com.transitionseverywhere.Slide
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_main_page.*


class MainPage : AppCompatActivity() {
    var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val slideViewPager = slideViewPager
        val sliderAdapter = SliderAdapter(this, this)
        slideViewPager.adapter = sliderAdapter
        addDots(0)
        dotsListener()

        rigth.setOnClickListener {
            slideViewPager.currentItem = currentPage + 1
        }
        left.setOnClickListener {
            slideViewPager.currentItem = currentPage - 1
        }
        var visible = false
        var isRotated = false
        slide.setOnClickListener {
            TransitionManager.beginDelayedTransition(top, Slide(Gravity.BOTTOM))
            visible = !visible
            isRotated = !isRotated
            logout.visibility = if (visible) View.VISIBLE else View.GONE
            switch_c.visibility = if (visible) View.VISIBLE else View.GONE
            TransitionManager.beginDelayedTransition(top,  Rotate())
            slide.rotation = if (isRotated) 90f else 0f
        }
        
        logout.setOnClickListener {
            getSharedPreferences(Consts.PREFERENCES_KEY, Context.MODE_PRIVATE)
                    .edit()
                    .putString(Consts.TOKEN, "")
                    .apply()
            startActivity(Intent(this, LoginPage::class.java))
        }

        switch_c.setOnClickListener {
            startActivity(Intent(this, SelectContext::class.java))
        }
    }


    private fun dotsListener() {
        slideViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(i: Int) {
                addDots(i)
                currentPage = i
                when (i) {
                    0 -> {
                        left.visibility = View.INVISIBLE
                        rigth.visibility = View.VISIBLE
                        rigth.text = "Dziecko"
                    }
                    1 -> {
                        left.visibility = View.VISIBLE
                        left.text = "Opłaty"
                        rigth.text = "Kontakt"
                    }
                    2 -> {
                        left.text = "Dziecko"
                        rigth.text = "Zgłoś"
                        rigth.visibility = View.VISIBLE
                    }
                    else -> {
                        rigth.visibility = View.INVISIBLE
                        left.text = "Kontakt"
                    }
                }
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageScrollStateChanged(p0: Int) {}
        })
    }

    private fun addDots(position: Int) {
        val arrayList = ArrayList<TextView>()
        dots.removeAllViews()
        for (i in 0..3) {
            val textView = TextView(this)
            textView.text = Html.fromHtml("&#8226")
            textView.textSize = 35F
            textView.setTextColor(Color.CYAN)
            arrayList.add(i, textView)
            dots.addView(arrayList[i])
        }

        if (arrayList.size > 0) {
            arrayList[position].setTextColor(Color.RED)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SecondPage.GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            CropImage
                    .activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val id = StorageUtils.getCurrentChild(this)
                PictureUtils.savePicture(result.uri, id)
                startActivity(Intent(this, MainPage::class.java))
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount === 0) {
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        val setIntent = Intent(Intent.ACTION_MAIN)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }
}
