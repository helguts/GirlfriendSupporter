package com.example.girlfriendsupporter.socialmedia.accounts

class TikTokAccount(username: String) : SocialMediaAccount(username) {

    override fun check(): Int {
        return 5
    }

    override fun like() {
        return
    }

    override fun share() {
        return
    }

}
