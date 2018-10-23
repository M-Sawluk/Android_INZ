package com.inzynier.michau.przedszkoletecza

import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.inzynier.michau.przedszkoletecza.slider.SliderAdapter
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

        changeChild.setOnClickListener {
            sliderAdapter.notifyDataSetChanged(slideViewPager.currentItem)
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

}
