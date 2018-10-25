package com.inzynier.michau.przedszkoletecza

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import kotlinx.android.synthetic.main.announcement.*
import java.text.SimpleDateFormat
import android.graphics.BitmapFactory
import android.graphics.Bitmap



class AnnoucementsPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.announcement)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val id = intent.getIntExtra("ann_id", 0)
        val newsModel = DataFetcher.getFullAnnouncement(this)[id]
        ann_news_title_.text = newsModel.title
        ann_news_author_.text = newsModel.author
        ann_news_content_.text = newsModel.description
        val dateToDisplayed = newsModel.date
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = formatter.format(dateToDisplayed)
        ann_news_data_.text = formattedDate
        val bmp = BitmapFactory.decodeByteArray(newsModel.image, 0, newsModel.image.size)
        ann_image.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.width, bmp.height, false))
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


}