package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.data.fetcher.RequestHelper
import kotlinx.android.synthetic.main.news.*
import java.text.SimpleDateFormat

class NewsPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val id = intent.getIntExtra("id", 0)
        val newsModel = DataFetcher.getFullNews(this)[id]
        news_title_.text = newsModel.title
        news_author_.text = newsModel.author
        news_content_.text = newsModel.description
        val dateToDisplayed = newsModel.date
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = formatter.format(dateToDisplayed)
        news_data_.text = formattedDate

        delete_news.setOnClickListener {
            RequestHelper
                    .makeRequest("/deleteMessage/${newsModel.id}", this)
            startActivity(Intent(this, MainPage::class.java))
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


}