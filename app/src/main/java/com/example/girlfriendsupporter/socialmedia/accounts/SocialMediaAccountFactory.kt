package com.example.girlfriendsupporter.socialmedia.accounts

import com.example.girlfriendsupporter.socialmedia.SocialMediaPlatform

class SocialMediaAccountFactory {
    fun createAccount(platform: SocialMediaPlatform, username: String): SocialMediaAccount {
        return when(platform) {
            SocialMediaPlatform.INSTAGRAM -> InstagramAccount(username)
            SocialMediaPlatform.TIKTOK -> TikTokAccount(username)
        }
    }

}
