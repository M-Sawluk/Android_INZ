package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.storage.StorageManager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.data.PostRequester
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_edit_absence_day.*
import org.json.JSONObject
import java.util.*

class EditAbsenceDay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_absence_day)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        abs_delete.isEnabled = false
        val day = intent.getParcelableExtra<CalendarDay>("day")
        val absenceRecords = StorageUtils.getAbsenceRecords(this)
        var id: Long ? = null
        for (absenceRecord in absenceRecords) {
            if(day.equals(CalendarDay.from(absenceRecord.absenceDate))){
                abs_reason.setText(absenceRecord.content)
                abs_add.text = "Modyfikuj"
                abs_delete.isEnabled = true
                id = absenceRecord.id
                abs_delete
                        .setOnClickListener {
                            deleteRecord(id)
                        }
            }
        }
        abs_date.text = day.date.toString()


        abs_add
                .setOnClickListener {
                    addAbsRecord(id)
                }
    }

    private fun addAbsRecord(id: Long?) {
        val absenceRecord = JSONObject()
                .put("absenceDate", abs_date.text)
        absenceRecord.put("id", id)
        if (abs_reason.text.isBlank()) {
            absenceRecord.put("reason", "Nie podano")
        } else {
            absenceRecord.put("reason", abs_reason.text)
        }
        val postRequester = PostRequester(this)
        val currentChildId = StorageUtils.getCurrentChildId(this)
        val makePostRequest = postRequester.makePostRequestWithList("childinfo/addAbsenceRecord/$currentChildId", Arrays.asList(absenceRecord))

        if(makePostRequest.isSuccessful) {
            val makeText = Toast.makeText(this, "Nieobecność dodana", Toast.LENGTH_SHORT)
            makeText.show()
        } else {
            val makeText = Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT)
            makeText.show()
        }
        val dataFetcher = DataFetcher(this)
        dataFetcher.fetchAbsenceRecords(StorageUtils.getCurrentChildId(this))
        dataFetcher.fetchBalanceStatus(StorageUtils.getCurrentChildId(this))
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }

    private fun deleteRecord(id: Long) {
        val dataFetcher = DataFetcher(this)
        val currentChildId = StorageUtils.getCurrentChildId(this)
        val makeRequest = dataFetcher.makeRequest("childinfo/deleteAbsenceRecord/$id/$currentChildId")
        dataFetcher.fetchBalanceStatus(currentChildId)
        if(makeRequest.isSuccessful) {
            val makeText = Toast.makeText(this, "Usunięto", Toast.LENGTH_SHORT)
            makeText.show()
        } else {
            val makeText = Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT)
            makeText.show()
        }
        DataFetcher(this).fetchAbsenceRecords(StorageUtils.getCurrentChildId(this))
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }


}
