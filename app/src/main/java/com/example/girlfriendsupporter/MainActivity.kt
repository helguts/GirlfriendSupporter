package com.example.girlfriendsupporter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
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

        setupEdits()
        setupButtons()

    }

    private fun setupEdits() {
        val editInstagram = findViewById<EditText>(R.id.edittext_instagram_user)
        val editTiktok = findViewById<EditText>(R.id.edittext_tiktok_user)

        editInstagram.doAfterTextChanged { enableCheckButton() }
        editTiktok.doAfterTextChanged { enableCheckButton() }
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

    private fun hasAtLeastOneUsername(): Boolean {
        val instagramUsername = findViewById<EditText>(R.id.edittext_instagram_user).text.toString()
        val tiktokUsername = findViewById<EditText>(R.id.edittext_tiktok_user).text.toString()
        return instagramUsername.isNotBlank() or tiktokUsername.isNotBlank()
    }

    private fun enableCheckButton() {
        val checkButton = findViewById<Button>(R.id.button_check)
        checkButton.isEnabled = hasAtLeastOneUsername()
    }

    private fun setupButtons() {
        val likeButton = findViewById<Button>(R.id.button_like)
        val shareButton = findViewById<Button>(R.id.button_share)
        val checkButton = findViewById<Button>(R.id.button_check)
        enableCheckButton()

        checkButton.setOnClickListener {
            updateSocialMediaOptions()
            doAsync {
                val newPostsCount = supporter.checkForNewPosts()
                uiThread {
                    likeButton.isEnabled = newPostsCount > 0
                    likeButton.text = if (newPostsCount > 0) getString(R.string.like_x,
                        newPostsCount) else getString(R.string.like)
                    shareButton.isEnabled = newPostsCount > 0
                    shareButton.text = if (newPostsCount > 0) getString(R.string.share_x,
                        newPostsCount) else getString(R.string.share)

                    val textLastChecked = findViewById<TextView>(R.id.textview_last_checked)
                    val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                    val currentTime = LocalDateTime.now().format(formatter)
                    textLastChecked.text = getString(R.string.last_checked, currentTime)
                }
            }
        }

        likeButton.setOnClickListener {
            doAsync {
                val likedPostsCount = supporter.likeNewPosts()
                uiThread {
                    showNumberOfAffectedPosts(R.id.textview_posts_liked, R.string.x_liked, likedPostsCount)
                    likeButton.text = getString(R.string.like)
                    likeButton.isEnabled = false
                }
            }
        }

        shareButton.setOnClickListener {
            doAsync {
                val sharedPostsCount = supporter.shareNewPosts()
                uiThread {
                    showNumberOfAffectedPosts(R.id.textview_posts_shared, R.string.x_shared, sharedPostsCount)
                    shareButton.text = getString(R.string.share)
                    shareButton.isEnabled = false
                }
            }
        }
    }

}