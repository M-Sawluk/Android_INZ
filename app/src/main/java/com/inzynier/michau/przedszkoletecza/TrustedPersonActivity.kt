package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.childInfo.trusted.ppl.TrustedPersonAdapter
import com.inzynier.michau.przedszkoletecza.data.PostRequester
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.data.fetcher.RequestHelper
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.transitionseverywhere.Slide
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_trusted_person.*
import org.json.JSONObject

class TrustedPersonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trusted_person)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val trusted = StorageUtils.getTrustedPpl(this)

        val trustedPersonAdapter = TrustedPersonAdapter(this, trusted)
        trustedPpl.adapter = trustedPersonAdapter

        addTrusted.setOnClickListener {
            val isOk : Boolean = performValidation()

            if(isOk) {
                val postRequester = PostRequester(this)
                val currentChildId = StorageUtils.getCurrentChildId(this)
                val trustedPerson = JSONObject()
                trustedPerson.put("name", t_name.text)
                trustedPerson.put("surname", t_surname.text)
                trustedPerson.put("civilId", t_civilId.text)
                trustedPerson.put("phoneNumber", t_phone.text)
                val result = postRequester
                        .makePostRequest("parent/addTrustedPerson/$currentChildId", trustedPerson)

                if(result.isSuccessful) {
                    val makeText = Toast.makeText(this, "${t_name.text} ${t_surname.text} dodana jako usoba upoważniona" , Toast.LENGTH_SHORT)
                    makeText.show()
                    val dataFetcher = DataFetcher(this)
                    dataFetcher.fetchTrustedPpl(currentChildId)
                    startActivity(Intent(this, TrustedPersonActivity::class.java))
                } else {
                    val makeText = Toast.makeText(this, "Wystąpił błąd" , Toast.LENGTH_SHORT)
                    makeText.show()
                }

            }


        }

        t_cancel_action_bar.setOnClickListener {
            TransitionManager.beginDelayedTransition(t_top_container, Slide(Gravity.BOTTOM))
            t_action_menu.visibility = View.GONE
        }

        t_delete.setOnClickListener {
            TransitionManager.beginDelayedTransition(t_top_container, Slide(Gravity.BOTTOM))
            t_action_menu.visibility = View.GONE
            val dataFetcher = DataFetcher(this)
            val currentChildId = StorageUtils.getCurrentChildId(this)
            dataFetcher.fetchTrustedPpl(currentChildId)
            RequestHelper.makeRequest("parent/deleteTrustedPerson/$currentChildId/${trustedPersonAdapter.positionToDelete}", this)
            startActivity(Intent(this, TrustedPersonActivity::class.java))
        }

        t_phone_img.setOnClickListener {
            TransitionManager.beginDelayedTransition(t_top_container, Slide(Gravity.BOTTOM))
            t_action_menu.visibility = View.GONE
            val phoneIntent = Intent(Intent.ACTION_CALL)
            phoneIntent.data = Uri.parse("tel:${trustedPersonAdapter.phone}")
            startActivity(phoneIntent)
        }

    }


    private fun performValidation(): Boolean {
        var isOk = true
        val name = t_name.text
        val surname = t_surname.text
        val civilId = t_civilId.text
        val phone = t_phone.text

        if (!name.matches(Regex("[A-ZŁ][a-zążźćńłóśę]+")) || name.length > 45) {
            t_validName.visibility = View.VISIBLE
            isOk = false
        } else {
            t_validName.visibility = View.INVISIBLE
        }
        if (!surname.matches(Regex("[A-ZŁŻŹŚ][a-zążźćńłóśę]+([- ][A-ZŁŻŹŚ][a-zążźćńłóśę]+)*")) || surname.length > 45) {
            t_validSurname.visibility = View.VISIBLE
            isOk = false
        } else {
            t_validSurname.visibility = View.INVISIBLE
        }
        if (!civilId.matches(Regex("[A-Z]{3}\\d{6}"))) {
            t_validCivilId.visibility = View.VISIBLE
            isOk = false
        } else {
            t_validCivilId.visibility = View.INVISIBLE
        }
        if (!phone.matches(Regex("\\d{9}"))) {
            t_validPhone.visibility = View.VISIBLE
            isOk = false
        } else {
            t_validPhone.visibility = View.INVISIBLE
        }

        return isOk
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
