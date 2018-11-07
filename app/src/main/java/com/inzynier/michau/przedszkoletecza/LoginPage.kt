package com.inzynier.michau.przedszkoletecza

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_register_form.*
import pl.droidsonroids.gif.GifImageView

class LoginPage : AppCompatActivity() {
    val PREFERENCES_KEY = "APP_SHRED_PREFS"
    val TOKEN = "TOKEN"
    var loadVal: GifImageView? = null
    val activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val token = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(TOKEN, "")

        if (!token.isBlank()) {
            zaloguj.visibility = View.INVISIBLE
            zarejestruj.visibility = View.INVISIBLE
            MyTask().execute(this)
        } else {
            load.visibility = View.INVISIBLE
            pobieram.visibility = View.GONE
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

    inner class MyTask : AsyncTask<Context, String, String>() {
        var context: Context? = null
        override fun doInBackground(vararg params: Context?): String {
            context = params[0]
            val dataFetcher = DataFetcher(activity)
            dataFetcher.fetchChild()
            val children = StorageUtils.getChildren(activity)
            var currentChild : Long
            if(children.contains(StorageUtils.getCurrentChilModel(activity)))
                currentChild = StorageUtils.getCurrentChildId(activity)
            else
                currentChild = children[0].id

            dataFetcher.fetchStartupData(currentChild)
            Thread.sleep(1000)
            val intent = Intent(context, MainPage::class.java)
            context?.startActivity(intent)

            return ""
        }

        override fun onPreExecute() {
            loadVal?.visibility = View.VISIBLE
        }
    }


}
