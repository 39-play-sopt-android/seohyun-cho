package org.sopt.play.ui.register

import android.util.Patterns

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
) {
    val isEmailError: Boolean
        get() = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordError: Boolean
        get() = password.isNotEmpty() && password.length < 6
    val isConfirmPasswordError: Boolean
        get() = confirmPassword.isNotEmpty() && confirmPassword != password
    val isRegisterEnabled: Boolean
        get() = name.isNotEmpty() && email.isNotEmpty() &&
            password.isNotEmpty() && confirmPassword.isNotEmpty() &&
            !isEmailError && !isPasswordError && !isConfirmPasswordError
}
