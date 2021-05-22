package com.example.girlfriendsupporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible

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
        checkButton.setOnClickListener {
            val checkedCount = 5
            likeButton.isEnabled = checkedCount > 0
            shareButton.isEnabled = checkedCount > 0

            showNumberOfAffectedPosts(R.id.textview_posts_found, R.string.x_new, checkedCount)
        }

        likeButton.setOnClickListener {
            val likedCount = 4
            showNumberOfAffectedPosts(R.id.textview_posts_liked, R.string.x_liked, likedCount)
            likeButton.isEnabled = false
        }

        shareButton.setOnClickListener {
            val sharedCount = 4
            showNumberOfAffectedPosts(R.id.textview_posts_shared, R.string.x_shared, sharedCount)
            shareButton.isEnabled = false
        }
    }

    private fun showNumberOfAffectedPosts(textViewId: Int, textId: Int, numberOfAffectedPosts: Int) {
        val textAffectedPosts = findViewById<TextView>(textViewId)
        textAffectedPosts.isVisible = true
        textAffectedPosts.text = getString(textId, numberOfAffectedPosts)
    }

}