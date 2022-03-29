package com.example.drawerlayout.common.entities

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val token: String,
    val img: String? = "",
    val user: String,
    val email: String
)