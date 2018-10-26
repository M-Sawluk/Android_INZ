package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.data.PostRequester
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_edit_absence_day.*
import org.json.JSONObject

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
        val absenceRecords = DataFetcher.getAbsenceRecords(this)
        for (absenceRecord in absenceRecords) {
            if(day.equals(CalendarDay.from(absenceRecord.absenceDate))){
                abs_reason.setText(absenceRecord.content)
                abs_add.text = "Modyfikuj"
                abs_delete.isEnabled = true
                abs_delete
                        .setOnClickListener {
                            deleteRecord(absenceRecord.id)
                        }
            }
        }
        abs_date.text = day.date.toString()


        abs_add
                .setOnClickListener {
                    addAbsRecord()
                }
    }

    private fun addAbsRecord() {
        val absenceRecord = JSONObject()
                .put("absenceDate", abs_date.text)
        if (abs_reason.text.isBlank()) {
            absenceRecord.put("reason", "Nie podano")
        } else {
            absenceRecord.put("reason", abs_reason.text)
        }
        val postRequester = PostRequester(this)

        val makePostRequest = postRequester.makePostRequest("childinfo/addAbsenceRecord", absenceRecord)

        if(makePostRequest.isSuccessful) {
            val makeText = Toast.makeText(this, "Nieobecność dodana", Toast.LENGTH_SHORT)
            makeText.show()
        } else {
            val makeText = Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT)
            makeText.show()
        }
        DataFetcher(this).fetchAbsenceRecords()
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }

    private fun deleteRecord(id: Long) {
        val dataFetcher = DataFetcher(this)
        val makeRequest = dataFetcher.makeRequest("childinfo/deleteAbsenceRecord/$id")

        if(makeRequest.isSuccessful) {
            val makeText = Toast.makeText(this, "Usunięto", Toast.LENGTH_SHORT)
            makeText.show()
        } else {
            val makeText = Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT)
            makeText.show()
        }
        DataFetcher(this).fetchAbsenceRecords()
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }


}
