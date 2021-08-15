package com.example.girlfriendsupporter.socialmedia

import com.example.girlfriendsupporter.UserNotFoundOnPlatformException
import com.example.girlfriendsupporter.socialmedia.accounts.SocialMediaAccount
import com.example.girlfriendsupporter.socialmedia.accounts.SocialMediaAccountFactory

class SocialMediaSupporter(private val options: SocialMediaOptions) {
    private val accounts = ArrayList<SocialMediaAccount>()

    @Throws(UserNotFoundOnPlatformException::class)
    fun checkForNewPosts(): Int {
        createAccounts()

        var countNewPosts = 0
        accounts.forEach{
            val newPostsOnPlatform = it.check()
            if (newPostsOnPlatform == 0) {
                throw UserNotFoundOnPlatformException(it.toString() + "not found!")
            }
            countNewPosts += newPostsOnPlatform
        }

        return countNewPosts
    }

    fun likeNewPosts(): Int {
        var countLikedPosts = 0
        accounts.forEach { countLikedPosts += it.like() }

        return countLikedPosts
    }

    fun shareNewPosts(): Int {
        var countSharedPosts = 0
        accounts.forEach{
            countSharedPosts += it.share()
        }

        return countSharedPosts
    }

    private fun createAccounts() {
        val accountFactory = SocialMediaAccountFactory()
        accounts.clear()
        options.usernameByPlatform.forEach{
            val account = accountFactory.createAccount(it.key, it.value)
            accounts.add(account)
        }
    }
}