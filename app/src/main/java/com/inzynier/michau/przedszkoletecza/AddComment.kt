package com.inzynier.michau.przedszkoletecza

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import com.inzynier.michau.przedszkoletecza.data.PostRequester
import com.inzynier.michau.przedszkoletecza.data.fetcher.DataFetcher
import com.inzynier.michau.przedszkoletecza.forum.CommentsAdapter
import com.inzynier.michau.przedszkoletecza.forum.Topic
import com.inzynier.michau.przedszkoletecza.utils.StorageUtils
import com.transitionseverywhere.Slide
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_add_comment.*
import org.json.JSONObject

class AddComment : AppCompatActivity() {
    var isAddCommentOn : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        startPost.setBackgroundResource(R.drawable.post)
        val topic = intent.getSerializableExtra("topic") as Topic

        postTitle.text = topic.title
        postData.text = postData.text.toString() + topic.creationDate
        postAuthor.text = postAuthor.text.toString() + topic.author.fullname
        postContent.text = topic.content

        addComment.setOnClickListener {
            TransitionManager.beginDelayedTransition(commentsConatiner, Slide(Gravity.BOTTOM))
            addCommentContainer.visibility = View.VISIBLE
            addComment.visibility = View.INVISIBLE
            isAddCommentOn = true
        }
        val comments = StorageUtils.getComments(this, topic.id)
        commentsList.adapter = CommentsAdapter(this, comments)

        saveComment
                .setOnClickListener {
                    val jsonObject = JSONObject()
                    jsonObject.put("topicId", topic.id)
                    jsonObject.put("content", comment_content.text)

                    val response = PostRequester(this)
                            .makePostRequest("news/addComment", jsonObject)
                    if(response.isSuccessful) {
                        DataFetcher(this).fetchForumData()
                        val intent = Intent(this, AddComment::class.java)
                        intent.putExtra("topic", topic)
                        startActivity(intent)
                    }
                }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount === 0) {
            if (isAddCommentOn) {
                TransitionManager.beginDelayedTransition(commentsConatiner, Slide(Gravity.BOTTOM))
                addCommentContainer.visibility = View.GONE
                addComment.visibility = View.VISIBLE
                isAddCommentOn = false
            } else {
                val setIntent = Intent(this, MainPage::class.java)
                setIntent.putExtra("page", 3)
                setIntent.addCategory(Intent.CATEGORY_HOME)
                setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(setIntent)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
