package com.inzynier.michau.przedszkoletecza

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    val PREFERENCES_KEY = "APP_SHRED_PREFS"
    val TOKEN = "TOKEN"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val token = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "")

        if(!token.isBlank()) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Znaleziono aktywny token nastepuje automatyczne zalogowanie")
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialog, id ->
                        val intent = Intent(this, MainPage::class.java)
                        startActivity(intent)
                    }
            val alert = builder.create()
            alert.show()
        }
        zaloguj.setOnClickListener {
            val intent = Intent(this, LoginForm::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(zaloguj,
                    "loginButtonTransition"))
            startActivity(intent, options.toBundle())
        }

        zarejestruj.setOnClickListener {
            val intent = Intent(this, RegisterForm::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(zarejestruj,
                    "registerButtonTransition"))
            startActivity(intent, options.toBundle())
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }



}
