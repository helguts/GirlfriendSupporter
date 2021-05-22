package com.example.girlfriendsupporter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.girlfriendsupporter.socialmedia.SocialMediaOptions
import com.example.girlfriendsupporter.socialmedia.SocialMediaPlatform
import com.example.girlfriendsupporter.socialmedia.SocialMediaSupporter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {
    private val socialMediaOptions = SocialMediaOptions()
    private val supporter = SocialMediaSupporter(socialMediaOptions)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSocialMedia()
        setupButtons()

    }

    private fun updateSocialMediaOptions() {
        val instagramUsername = findViewById<EditText>(R.id.edittext_instagram_user).text.toString()
        if (instagramUsername.isNotBlank())
            socialMediaOptions.addPlatform(SocialMediaPlatform.INSTAGRAM, instagramUsername)

        val tiktokUsername = findViewById<EditText>(R.id.edittext_tiktok_user).text.toString()
        if (tiktokUsername.isNotBlank())
            socialMediaOptions.addPlatform(SocialMediaPlatform.TIKTOK, tiktokUsername)
    }

    private fun showNumberOfAffectedPosts(textViewId: Int, textId: Int, numberOfAffectedPosts: Int) {
        val textAffectedPosts = findViewById<TextView>(textViewId)
        textAffectedPosts.isVisible = true
        textAffectedPosts.text = getString(textId, numberOfAffectedPosts)
    }

    private fun setupButtons() {
        val likeButton = findViewById<Button>(R.id.button_like)
        val shareButton = findViewById<Button>(R.id.button_share)
        val checkButton = findViewById<Button>(R.id.button_check)

        var newPostsCount = 0
        checkButton.setOnClickListener {
            updateSocialMediaOptions()

            try {
                doAsync {
                    newPostsCount = supporter.checkForNewPosts()
                    uiThread {
                        likeButton.isEnabled = newPostsCount > 0
                        likeButton.text = if (newPostsCount > 0) getString(R.string.like_x, newPostsCount) else getString(R.string.like)
                        shareButton.isEnabled = newPostsCount > 0
                        shareButton.text = if (newPostsCount > 0) getString(R.string.share_x, newPostsCount) else getString(R.string.share)

                        val textLastChecked = findViewById<TextView>(R.id.textview_last_checked)
                        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                        val currentTime = LocalDateTime.now().format(formatter)
                        textLastChecked.text = getString(R.string.last_checked, currentTime)
                    }
                }
            } catch (e: UserNotFoundOnPlatformException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
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

    private fun setupSocialMedia() {
        val checkButton = findViewById<Button>(R.id.button_check)
        val instaCheckbox = findViewById<CheckBox>(R.id.checkbox_instagram)
        val tiktokCheckbox = findViewById<CheckBox>(R.id.checkbox_tiktok)

        instaCheckbox.setOnClickListener {
            val instaUsername = findViewById<EditText>(R.id.edittext_instagram_user)
            instaUsername.isEnabled = instaCheckbox.isChecked
            checkButton.isEnabled = instaCheckbox.isChecked or tiktokCheckbox.isChecked
        }

        tiktokCheckbox.setOnClickListener {
            val tiktokUsername = findViewById<EditText>(R.id.edittext_tiktok_user)
            tiktokUsername.isEnabled = tiktokCheckbox.isChecked
            checkButton.isEnabled = instaCheckbox.isChecked or tiktokCheckbox.isChecked
        }
    }

}