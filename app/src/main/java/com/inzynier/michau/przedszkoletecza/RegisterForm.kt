package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_register_form.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class RegisterForm : AppCompatActivity() {
    private val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        zarejestruj.setOnClickListener {
            sentRegistrationData()
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

    private fun sentRegistrationData() {
        val isFormOk = performValidation()
        if(isFormOk) {
            val url = Consts.IP + ":8080/tecza/register/parent"
            val body = JSONObject()
                    .put("name", name.text.toString())
                    .put("surname", surname.text.toString())
                    .put("civilId", civilId.text.toString())
                    .put("email", email.text.toString())
                    .put("phoneNumber", phone.text.toString())
                    .put("password", password.text.toString())
                    .put("matchingPassword", repeatPassword.text.toString())

            val mediaType = MediaType.parse("application/json; charset=utf-8")

            val request = Request.Builder()
                    .url(url)
                    .post(RequestBody.create(mediaType, body.toString()))
                    .build()

            val response = httpClient
                    .newCall(request)
                    .execute()

            if (response.isSuccessful) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Udało Ci się zarejestrować! Następnie udaj się do pracownika przedszkola w celu aktywacji konta i podpisania umowy. NA POTRZEBY DEMO KONTO JEST JUZ AKTYWNE")
                        .setCancelable(false)
                        .setPositiveButton("OK") { dialog, id ->
                            val intent = Intent(this, LoginPage::class.java)
                            startActivity(intent)
                        }
                val alert = builder.create()
                alert.show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(response.message())
                        .setCancelable(false)
                        .setPositiveButton("OK") { dialog, id ->
                            val intent = Intent(this, LoginPage::class.java)
                            startActivity(intent)
                        }
                val alert = builder.create()
                alert.show()
            }
        }
    }

    private fun performValidation(): Boolean {
        var isOk = true
        val name = name.text
        val surname = surname.text
        val civilId = civilId.text
        val email = email.text
        val phone = phone.text
        val password = password.text
        val repeatPassword = repeatPassword.text

        if (!name.matches(Regex("[A-ZŁ][a-zążźćńłóśę]+")) || name.length > 45) {
            validName.visibility = View.VISIBLE
            isOk = false
        } else {
            validName.visibility = View.INVISIBLE
        }
        if (!surname.matches(Regex("[A-ZŁŻŹŚ][a-zążźćńłóśę]+([- ][A-ZŁŻŹŚ][a-zążźćńłóśę]+)*")) || surname.length > 45) {
            validSurname.visibility = View.VISIBLE
            isOk = false
        } else {
            validSurname.visibility = View.INVISIBLE
        }
        if (!civilId.matches(Regex("[A-Z]{3}\\d{6}"))) {
            validCivilId.visibility = View.VISIBLE
            isOk = false
        } else {
            validCivilId.visibility = View.INVISIBLE
        }
        if (!email.matches(Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))) {
            validEmail.visibility = View.VISIBLE
            isOk = false
        } else {
            validEmail.visibility = View.INVISIBLE
        }
        if (!phone.matches(Regex("\\d{9}"))) {
            validPhone.visibility = View.VISIBLE
            isOk = false
        } else {
            validPhone.visibility = View.INVISIBLE
        }
        if (!password.matches(Regex("[A-Za-z0-9]{9,30}"))) {
            validPassword.visibility = View.VISIBLE
            isOk = false
        } else {
            validPassword.visibility = View.INVISIBLE
        }
        if (!password.toString().equals(repeatPassword.toString())) {
            validRepeatPassword.visibility = View.VISIBLE
            isOk = false
        } else {
            validRepeatPassword.visibility = View.INVISIBLE
        }

        return isOk
    }
}
