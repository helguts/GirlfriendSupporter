package com.example.girlfriendsupporter.socialmedia.accounts

import java.net.URL
import java.nio.charset.Charset
import java.util.regex.Pattern

class InstagramAccount(username: String) : SocialMediaAccount(username) {
    private val postIds = ArrayList<String>()

    override fun check(): Int {
        postIds.clear()
        var countNewPosts = 0

        // TODO: how to check, if a user exists?
        val userPage = URL("https://www.instagram.com/$username")
        val content = userPage.readText(Charset.forName("ISO-8859-1"))
        val postPattern = Pattern.compile("\"shortcode\":\"(.*?)\"")
        val matcher = postPattern.matcher(content)

        while (matcher.find()) {
            val postId = matcher.group(1)
            postIds.add(postId)

            countNewPosts++
        }
        return countNewPosts
    }

    private fun likePost(postID: String) {
        val postPage = URL("https://www.instagram.com/p/$postID")
        val content = postPage.readText(Charset.forName("ISO-8859-1"))
        val buttonPattern = Pattern.compile("button")
        val matcher = buttonPattern.matcher(content)

        while (matcher.find()) {
            val likeButton = matcher.group(1)
            clickOnButton(likeButton)
        }
    }

    private fun clickOnButton(button: String) {
        // todo
    }

    override fun like(): Int {
        // loops through the posts and clicks on the first button, which hopefully is the "like"-button
        postIds.forEach {
            likePost(it)
        }
        return postIds.count()
    }

    override fun toString(): String {
        return "Instagram: $username"
    }

    override fun share(): Int {
        return postIds.count()
    }
}