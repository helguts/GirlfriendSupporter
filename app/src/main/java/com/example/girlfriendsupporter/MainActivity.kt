package com.example.girlfriendsupporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val checkTestCount = 5
        val checkButton = findViewById<Button>(R.id.button_check)
        checkButton.setOnClickListener {
            val buttonLike = findViewById<Button>(R.id.button_like)
            buttonLike.isEnabled = checkTestCount > 0
            val buttonShare = findViewById<Button>(R.id.button_share)
            buttonShare.isEnabled = checkTestCount > 0

            val textPostsFound = findViewById<TextView>(R.id.textview_posts_found)
            textPostsFound.isVisible = true
            textPostsFound.text = getString(R.string.x_new_posts, checkTestCount)
        }
    }

}