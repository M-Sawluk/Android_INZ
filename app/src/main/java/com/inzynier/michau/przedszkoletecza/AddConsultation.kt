package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationHours
import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationModel
import com.inzynier.michau.przedszkoletecza.data.PostRequester
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_add_consultation.*
import org.json.JSONObject
import org.threeten.bp.LocalDate

class AddConsultation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_consultation)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val cons = intent.getSerializableExtra("consultation") as ConsultationModel
        val day = intent.getStringExtra("day")
                .replace("{", "")
                .replace("}","")
                .replace("CalendarDay","")

        consDay.text = day

        teacherName.text = cons.user.fullname
        timeSpinner.adapter = ArrayAdapter<String>(this, R.layout.spinner_row, getHours(cons, day))


        addConsultation.setOnClickListener {
            val currentChildId = StorageUtils.getCurrentChildId(this)
            val json = JSONObject()
            json.put("consultationId", getConsId(cons, day))
            json.put("childId", currentChildId)
            json.put("hour", timeSpinner.selectedItem.toString().split(":")[0])
            json.put("min", timeSpinner.selectedItem.toString().split(":")[1])

            val postRequester = PostRequester(this)

            val response = postRequester.makePostRequest("parent/bookConsultation", json)

            if(response.isSuccessful) {
                DataFetcher(this).fetchConsultationData()
                val makeText = Toast.makeText(this, "Dodano", Toast.LENGTH_SHORT)
                makeText.show()
                startActivity(Intent(this, ConsultationActivity::class.java))
            } else {
                val makeText = Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT)
                makeText.show()
            }
        }
    }

    private fun getHours(cons: ConsultationModel, calendarDay: String) : List<String>{
        val split = calendarDay.split("-")
        val day = LocalDate.of(split[0].toInt(), split[1].toInt(), split[2].toInt())
        val hours = ArrayList<String>()
        for (cday in cons.days) {
            if(cday.localDate.isEqual(day)) {
                for (hour in cday.hours) {
                    hours.add(hour.hour.toString() + ":" + hour.min.toString())
                }
            }
        }

        return hours
    }

    private fun getConsId(cons: ConsultationModel, calendarDay: String): Long {
        val split = calendarDay.split("-")
        val calendarDay = LocalDate.of(split[0].toInt(), split[1].toInt(), split[2].toInt())

        for (day in cons.days) {
            if (day.localDate.isEqual(calendarDay))
                return day.id
        }

        return 0L
    }
}
