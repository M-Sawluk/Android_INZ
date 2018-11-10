package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_consultation.*
import android.widget.ArrayAdapter
import com.inzynier.michau.przedszkoletecza.consultation.model.ConsultationModel
import com.inzynier.michau.przedszkoletecza.slider.slider.parts.EventDecorator
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import java.util.*


class ConsultationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val consultations = StorageUtils.getConsultations(this)
        val fullnames = ArrayList<String>()
        for (consultation in consultations) {
            fullnames.add(consultation.user.fullname)
        }
        val adapter = ArrayAdapter<String>(this, R.layout.spinner_row, fullnames)
        spinnerT.adapter = adapter

        val calendar = consCalendar
        calendar.selectionColor = Color.BLUE

        spinnerT.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val dates = getDates(consultations, position)
                calendar.removeDecorators()
                calendar.currentDate = CalendarDay.from(LocalDate.now())
                calendar.addDecorators(getMarkeDays(dates))
            }
        }

        calendar.setOnDateLongClickListener { _, calendarDay ->
            if(onList(calendarDay, consultations)) {
                val addConsultation = Intent(this, AddConsultation::class.java)
                addConsultation.putExtra("day", calendarDay.toString())
                addConsultation.putExtra("consultation", getConsultation(consultations))
                startActivity(addConsultation)
            }
        }

        calendar.setOnDateChangedListener { adapter, calendarDay, _ ->
            val titles = getTitles(calendarDay, consultations)
            consultText.text = titles
            if (titles.isBlank())
                consultText.text = "Zapisz się"
        }
    }

    private fun onList(calendarDay: CalendarDay, consultations: MutableList<ConsultationModel>): Boolean {
        var onList = false

        for (consultation in consultations) {
            for (day in consultation.days) {
                if (day.localDate.isEqual(calendarDay.date)) {
                    onList = true
                }
            }
        }

        return onList
    }

    private fun getMarkeDays(list: List<LocalDate>): List<EventDecorator> {
        val decorators = ArrayList<EventDecorator>()
        val presenceDays = ArrayList<CalendarDay>()

        for (localDate in list) {
            presenceDays.add(CalendarDay.from(localDate))
        }
        decorators.add(EventDecorator(Color.GREEN, presenceDays, 5f))
        return decorators
    }

    private fun getDates(list: List<ConsultationModel>, position: Int): List<LocalDate> {
        val arrayList = ArrayList<LocalDate>()

        val consultationModel = list[position]
        val days = consultationModel.days
        for (day in days) {
            arrayList.add(day.localDate)
        }
        return arrayList
    }


    private fun getTitles(cday : CalendarDay , cons : List<ConsultationModel>) : String {
        val selectedItem = spinnerT.selectedItem
        var title = ""
        for (con in cons) {
            if (con.user.fullname.equals(selectedItem)) {
                val days = con.days
                for (day in days) {
                    if (day.localDate.isEqual(cday.date)) {
                        val hours = day.hours
                        title = "W godzinach: " + hours[0].hour.toString()+":"+ hours[0].min.toString()+ "-" + hours[hours.size-1].hour.toString()+":"+ hours[hours.size-1].min.toString()
                    }
                }
            }
        }
        return title
    }

    private fun getConsultation(consultations: List<ConsultationModel>) : ConsultationModel {
        for (consultation in consultations) {
            if (consultation.user.fullname.equals(spinnerT.selectedItem)) {
                return consultation
            }
        }
        throw IllegalStateException("Wrond date")
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
