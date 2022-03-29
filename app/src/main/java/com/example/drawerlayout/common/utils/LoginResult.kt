package com.example.drawerlayout.common.utils

import com.example.drawerlayout.common.entities.ErrorDetails
import com.example.drawerlayout.common.entities.ErrorRegister
import com.example.drawerlayout.common.entities.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult (
     val success: LoggedInUserView? = null,
     val error:String? = null,
     val logout: LoggedInUserView? = null,
     val errorFields: ErrorDetails? = null
)