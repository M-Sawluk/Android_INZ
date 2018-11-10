package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.KeyEvent
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.data.fetcher.RequestHelper
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import kotlinx.android.synthetic.main.activity_alergy.*

class AlergyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alergy)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val currentChildId = StorageUtils.getCurrentChildId(this)

        val alergens = RequestHelper
                .makeRequest("parent/getChildAdditionalInfo/$currentChildId", this)
                .body()?.string()

        if(alergens != null) {
            alergyInput.setText(alergens)
        }

        alergy_button.setOnClickListener {
            val alergensUpdated = alergyInput.text.toString()
            RequestHelper
                    .makeRequest("parent/setChildAdditionalInfo/$currentChildId/$alergensUpdated", this)
            startActivity(Intent(this, MainPage::class.java))
        }
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
