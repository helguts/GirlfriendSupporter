package com.example.girlfriendsupporter.socialmedia.accounts

class TikTokAccount(username: String) : SocialMediaAccount(username) {

    override fun check(): Int {
        return 5
    }

    override fun like(): Int {
        return 0
    }

    override fun share(): Int {
        return 0
    }

    override fun toString(): String {
        return "TikTok: $username"
    }

}
