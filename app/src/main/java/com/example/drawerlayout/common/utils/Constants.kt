package com.example.drawerlayout.common.utils

object Constants {
    // All Stores
    const val URL = "https://explore-2021.herokuapp.com"
    const val GET_ALL_STORES = "/api/products"
    const val POST_LOGIN = "/api/user/signin"
    const val POST_REGISTER = "/api/user/signup"
    const val POST_COMMENT = "/api/products/comments/"

    const val STATUS_PROPERTY = "success"
    const val PRODUCTS_PROPERTY = "result"
    const val LOGIN_PROPERTY = "response"
    const val ERROR_PROPERTY = "error"

    // Login and Signup
    const val EMAIL_PARAM = "email"
    const val PASSWORD_PARAM = "password"
    const val USERNAME_PARAM = "user"
    const val PHOTO_PARAM = "urlImg"

    // Comments
    const val COMMENT_PARAM = "comment"
    const val TOKEN_PARAM = "token"

    const val SUCCESS = true
    const val ERROR = false

    const val SHOW = true
    const val HIDE = false
}