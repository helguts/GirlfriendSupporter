package com.example.girlfriendsupporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val instaCheckbox = findViewById<CheckBox>(R.id.checkbox_instagram)
        instaCheckbox.setOnClickListener {
            val instaUsername = findViewById<EditText>(R.id.edittext_instagram_user)
            instaUsername.isEnabled = instaCheckbox.isChecked
        }

        val tiktokCheckbox = findViewById<CheckBox>(R.id.checkbox_tiktok)
        tiktokCheckbox.setOnClickListener {
            val tiktokUsername = findViewById<EditText>(R.id.edittext_tiktok_user)
            tiktokUsername.isEnabled = tiktokCheckbox.isChecked
        }

        val likeButton = findViewById<Button>(R.id.button_like)
        val shareButton = findViewById<Button>(R.id.button_share)
        val checkButton = findViewById<Button>(R.id.button_check)
        val newPostsCount = 5
        checkButton.setOnClickListener {
            likeButton.isEnabled = newPostsCount > 0
            likeButton.text = if (newPostsCount > 0) getString(R.string.like_x, newPostsCount) else getString(R.string.like)
            shareButton.isEnabled = newPostsCount > 0
            shareButton.text = if (newPostsCount > 0) getString(R.string.share_x, newPostsCount) else getString(R.string.share)

            val textLastChecked = findViewById<TextView>(R.id.textview_last_checked)
            val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            val currentTime = LocalDateTime.now().format(formatter)
            textLastChecked.text = getString(R.string.last_checked, currentTime)
        }

        likeButton.setOnClickListener {
            showNumberOfAffectedPosts(R.id.textview_posts_liked, R.string.x_liked, newPostsCount)
            likeButton.text = getString(R.string.like)
            likeButton.isEnabled = false
        }

        shareButton.setOnClickListener {
            showNumberOfAffectedPosts(R.id.textview_posts_shared, R.string.x_shared, newPostsCount)
            shareButton.text = getString(R.string.share)
            shareButton.isEnabled = false
        }
    }

    private fun showNumberOfAffectedPosts(textViewId: Int, textId: Int, numberOfAffectedPosts: Int) {
        val textAffectedPosts = findViewById<TextView>(textViewId)
        textAffectedPosts.isVisible = true
        textAffectedPosts.text = getString(textId, numberOfAffectedPosts)
    }

}