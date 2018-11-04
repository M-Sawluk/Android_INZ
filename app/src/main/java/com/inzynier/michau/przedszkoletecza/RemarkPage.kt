package com.inzynier.michau.przedszkoletecza

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemarkAdapter
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.transitionseverywhere.Slide
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.activity_remark_page.*
import android.widget.AdapterView.OnItemLongClickListener




class RemarkPage : AppCompatActivity() {
    var remarks  = arrayListOf<RemakrsDto>()
    val selectedRemarks = arrayListOf<RemakrsDto>()
    var count : Int = 0
    var context : Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remark_page)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        context = this
        remarks.addAll(StorageUtils.getRemarksList(this))
        val remarkAdapter = RemarkAdapter(this, remarks)
        remark_list_view.adapter = remarkAdapter

        cancel_action_bar.setOnClickListener {
            TransitionManager.beginDelayedTransition(top_container, Slide(Gravity.BOTTOM))
            action_menu.visibility = View.GONE
        }

        setasread.setOnClickListener {
            val makeText = Toast.makeText(context, "Uwaga została oznaczona jako przeczytana" , Toast.LENGTH_SHORT)
                makeText.show()
                val dataFetcher = DataFetcher(context)
                dataFetcher.makeRequest("childinfo/setAsRead/${remarkAdapter.positionToDelete}")
                val id = StorageUtils.getCurrentChildId(context)
                dataFetcher.fetchChildRemarks(id)
            TransitionManager.beginDelayedTransition(top_container, Slide(Gravity.BOTTOM))
            action_menu.visibility = View.GONE
            startActivity(Intent(this, RemarkPage::class.java))
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
        val setIntent = Intent(this, MainPage::class.java)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }
}
