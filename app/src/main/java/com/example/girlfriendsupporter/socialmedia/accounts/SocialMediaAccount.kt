package com.example.girlfriendsupporter.socialmedia.accounts

abstract class SocialMediaAccount(username: String) {
    protected val username = username

    abstract fun check(): Int
    abstract fun like(): Int
    abstract fun share(): Int
}