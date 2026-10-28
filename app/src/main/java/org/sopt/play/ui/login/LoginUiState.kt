package org.sopt.play.ui.login

import android.util.Patterns

data class LoginUiState(
    val email: String = "",
    val password: String = "",
) {
    val isEmailError: Boolean
        get() = email.isNotEmpty() &&
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError: Boolean
        get() = password.isNotEmpty() && password.length < 6

    val isLoginEnabled: Boolean
        get() = email.isNotEmpty() &&
                password.isNotEmpty() &&
                !isEmailError &&
                !isPasswordError
}