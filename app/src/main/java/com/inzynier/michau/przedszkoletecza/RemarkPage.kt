package com.inzynier.michau.przedszkoletecza

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.ListView
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemarkAdapter
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import kotlinx.android.synthetic.main.activity_remark_page.*


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
        remark_list_view.adapter = RemarkAdapter(this, remarks)
        remark_list_view.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL

        remark_list_view.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                val makeText = Toast.makeText(context, "Uwagi zostały oznaczone jako przeczytane" , Toast.LENGTH_SHORT)
                makeText.show()
                val dataFetcher = DataFetcher(context)
                for (selectedRemark in selectedRemarks) {
                    dataFetcher.makeRequest("childinfo/setAsRead/${selectedRemark.id}")
                }
                val id = StorageUtils.getCurrentChild(context)
                dataFetcher.fetchChildRemarks(id)
                val intent = Intent(context, MainPage::class.java)
                context?.startActivity(intent)
                return true
            }

            override fun onItemCheckedStateChanged(mode: ActionMode?, position: Int, id: Long, checked: Boolean) {
                if(!selectedRemarks.contains(remarks[position])) {
                    count ++
                    mode?.title = "Ustaw jako przeczytane"
                    selectedRemarks.add(remarks[position])
                }
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                val menuInflater = mode?.menuInflater
                menuInflater?.inflate(R.menu.menu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                selectedRemarks.clear()
                count = 0
            }


        })
    }
}
