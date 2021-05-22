package com.example.girlfriendsupporter.socialmedia


class SocialMediaOptions {
    val usernameByPlatform = HashMap<SocialMediaPlatform, String>()

    fun addPlatform(platform: SocialMediaPlatform, username: String) {
        usernameByPlatform[platform] = username.trim()
    }

}
