package com.example.drawerlayout.common.entities

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    var token: String = "",
    var user: String,
    var email: String = "",
    var urlImg: String = "",
    //... other data fields that may be accessible to the UI
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LoggedInUserView
        if (email != other.email) return false
        return true
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}