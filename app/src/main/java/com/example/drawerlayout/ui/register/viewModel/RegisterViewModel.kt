package com.example.drawerlayout.ui.register.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawerlayout.common.entities.LoggedInUserView
import com.example.drawerlayout.common.utils.LoginFormState
import com.example.drawerlayout.common.utils.LoginResult
import com.example.drawerlayout.ui.register.model.RegisterInteractorInput

class RegisterViewModel: ViewModel() {
    private val interactor: RegisterInteractorInput = RegisterInteractorInput()

    private val _registerForm = MutableLiveData<LoginFormState>()
    val registerFormState: LiveData<LoginFormState> = _registerForm

    private val _registerResult = MutableLiveData<LoginResult>()
    val registerResult: LiveData<LoginResult> = _registerResult

    fun register(username: String, password: String, lastname: String, photoUri: String) {
        interactor.register(username,password,lastname,photoUri) { registerUser, err ->
            if (registerUser != null) {
                _registerResult.value = LoginResult(success = LoggedInUserView(
                    user = registerUser.name,
                    email = registerUser.email,
                ))
            } else {
                _registerResult.value = LoginResult(errorFields = err)
            }
        }
    }

    fun logOut() {
        _registerResult.value = LoginResult(logout = LoggedInUserView(
            user = "Welcome to android studio",
            email = "android.studio@android.com",
            urlImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyONMl-oynWzLcxAAnvZLNgCyrwu7p9UhgcA&usqp=CAU"
        ),
            success = null
        )
    }

    fun registerDataChanged(username: String, password: String, lastname: String) {
//        if (!isUserNameValid(username)) {
//            _registerForm.value = LoginFormState(usernameError = R.string.invalid_username)
//        } else if (!isPasswordValid(password)) {
//            _registerForm.value = LoginFormState(passwordError = R.string.invalid_password)
//        } else {
//            _registerForm.value = LoginFormState(isDataValid = true)
//        }
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