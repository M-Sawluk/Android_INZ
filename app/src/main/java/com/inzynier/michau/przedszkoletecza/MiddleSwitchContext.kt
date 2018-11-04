package com.inzynier.michau.przedszkoletecza

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.childInfo.ChildModel
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.utils.PictureUtils
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import kotlinx.android.synthetic.main.activity_middle_switch_context.*


class MiddleSwitchContext : AppCompatActivity() {
    val activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle_switch_context)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val child : ChildModel = intent.getSerializableExtra("child") as ChildModel
        temp_pic.setImageBitmap(PictureUtils.getPictureForChild(child.id, this))
        temp_pic.visibility = View.VISIBLE
        MyTask().execute(child.id)

    }

    inner class MyTask : AsyncTask<Long, String, String>() {
        var id: Long? = null
        override fun doInBackground(vararg params: Long?): String {
            StorageUtils.setCurrentChildId(activity, params[0]!!.or(0L))
            val dataFetcher = DataFetcher(activity)
            dataFetcher.fetchStartupData(params[0]!!.or(0L))
            startActivity(Intent(activity, MainPage::class.java))
            return ""
        }
    }
}
