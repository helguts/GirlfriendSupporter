package com.example.girlfriendsupporter.socialmedia

import com.example.girlfriendsupporter.socialmedia.accounts.SocialMediaAccount
import com.example.girlfriendsupporter.socialmedia.accounts.SocialMediaAccountFactory

class SocialMediaSupporter(options: SocialMediaOptions) {
    private val options = options
    private val accounts = ArrayList<SocialMediaAccount>()

    fun checkForNewPosts(): Int {
        createAccounts()

        var countNewPosts = 0
        accounts.forEach{
            countNewPosts += it.check()
        }

        return countNewPosts
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