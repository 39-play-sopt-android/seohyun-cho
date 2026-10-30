package org.sopt.play.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }

    private var lastRegisteredCredentials: Pair<String, String>? = null

    fun setRegisteredCredentials(email: String, password: String) {
        val credentials = email to password
        if (lastRegisteredCredentials == credentials) return
        lastRegisteredCredentials = credentials
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                password = password,
            )
        }
    }

    fun login(
        registeredEmail: String,
        registeredPassword: String,
    ): Boolean {
        val currentState = _uiState.value

        return currentState.isLoginEnabled &&
                registeredEmail.isNotEmpty() &&
                registeredPassword.isNotEmpty() &&
                currentState.email == registeredEmail &&
                currentState.password == registeredPassword
    }

}