package com.example.drawerlayout.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.drawerlayout.R
import com.example.drawerlayout.common.entities.LoggedInUserView
import com.example.drawerlayout.common.utils.LoginFormState
import com.example.drawerlayout.common.utils.LoginResult
import com.example.drawerlayout.ui.login.model.LoginInteractor

class LoginViewModel: ViewModel() {

    private val interactor: LoginInteractor = LoginInteractor()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        interactor.login(username,password) { loggedUser, err ->
            if (loggedUser != null) {
                _loginResult.value = LoginResult(success = LoggedInUserView(
                    token = loggedUser.token,
                    user = loggedUser.user,
                    email = loggedUser.email,
                    urlImg = loggedUser.img ?: ""
                ))
            } else {
                _loginResult.value = LoginResult(error = err)
            }
        }
    }

    fun logOut() {
        _loginResult.value = LoginResult(logout = LoggedInUserView(
            user = "Welcome to android studio",
            email = "android.studio@android.com",
            urlImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyONMl-oynWzLcxAAnvZLNgCyrwu7p9UhgcA&usqp=CAU"
        ),
            success = null
        )
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}