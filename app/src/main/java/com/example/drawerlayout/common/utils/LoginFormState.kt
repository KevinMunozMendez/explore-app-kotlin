package com.example.drawerlayout.common.utils

/**
 * Data validation state of the login form.
 */
data class LoginFormState (
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val lastNameError: Int? = null,
    val isDataValid: Boolean = false)