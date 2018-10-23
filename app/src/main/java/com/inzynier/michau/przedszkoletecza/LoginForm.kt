package com.inzynier.michau.przedszkoletecza

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login_form.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import com.inzynier.michau.przedszkoletecza.utils.Consts


class LoginForm : AppCompatActivity() {
    val httpClient = OkHttpClient()
    val PREFERENCES_KEY = "APP_SHRED_PREFS"
    val TOKEN = "TOKEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        zaloguj.setOnClickListener {
            login()
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


    private fun login() {
        val url = Consts.IP + ":8080/token/login"

        val body = JSONObject()
                .put("email", emailForm.text.toString())
                .put("password", passwordForm.text.toString())

        val mediaType = MediaType.parse("application/json; charset=utf-8")

        val request = Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, body.toString()))
                .addHeader("User-Agent", "Android")
                .build()

        val response = httpClient
                .newCall(request)
                .execute()

        if (response.isSuccessful) {
            val body = response.body()?.string()
            val jObject = JSONObject(body)
            val token = jObject.getString("token")

            getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .edit()
                .putString(TOKEN, token)
                .apply()

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Poprawne logowanie, token zostal przyznany na 30 dni.")
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialog, id ->
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                    }
            val alert = builder.create()
            alert.show()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Wystąpił problem")
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
