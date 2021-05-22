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

    override fun like() {
        return
    }

    override fun share() {
        return
    }
}