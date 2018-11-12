package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.Toast
import com.inzynier.michau.przedszkoletecza.data.PostRequester
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.forum.ForumAdapter
import com.inzynier.michau.przedszkoletecza.forum.Topic
import kotlinx.android.synthetic.main.activity_add_topic.*
import org.json.JSONObject

class AddTopic : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_topic)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val topic = intent.getSerializableExtra("topic")

        if (topic != null) {
            topic as Topic
            topicTitle.setText(topic.title)
            topicTitle.isEnabled = false
            topicContent.setText(topic.content)
        }

        addTopicButton.setOnClickListener {
            val postRequester = PostRequester(this)
            val jsonObject = JSONObject()
            if(topic != null) {
                topic as Topic
                jsonObject.put("id", topic.id)
            }
            jsonObject.put("title", topicTitle.text)
            jsonObject.put("content", topicContent.text)
            val response = postRequester.makePostRequest("news/addTopic", jsonObject)

            if(response.isSuccessful) {
                DataFetcher(this).fetchForumData()
                var message : String
                if (topic != null) {
                    message = "Zaktualizowano"
                } else {
                    message ="Dodano nowy wątek"
                }
                val makeText = Toast.makeText(this, message, Toast.LENGTH_SHORT)
                makeText.show()
                val intent = Intent(this, MainPage::class.java)
                intent.putExtra("page", 3)
                startActivity(intent)
            } else {
                val makeText = Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT)
                makeText.show()
            }

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
        setIntent.putExtra("page", 3)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }
}
