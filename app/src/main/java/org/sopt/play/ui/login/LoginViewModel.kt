package org.sopt.play.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.data.LocalStorage
import org.sopt.play.data.api.SearchApiFactory
import org.sopt.play.data.repository.AuthRepository
import org.sopt.play.ui.login.model.LoginStatus

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private var isLoggingIn = false
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
    ) {
        if (isLoggingIn || _uiState.value.loginStatus == LoginStatus.SUCCESS) {
            return
        }

        val currentState = _uiState.value

        val isCredentialsMatch =
            registeredEmail.isNotEmpty() &&
                    registeredPassword.isNotEmpty() &&
                    currentState.email == registeredEmail &&
                    currentState.password == registeredPassword

        if (!currentState.isLoginEnabled || !isCredentialsMatch) {
            _uiState.update { state ->
                state.copy(loginStatus = LoginStatus.FAILURE)
            }
            return
        }

        isLoggingIn = true

        viewModelScope.launch {
            try {
                // 인증 코드 발급과 저장을 Repository에 요청
                authRepository.issueAuthorizationCode()
                    .onSuccess {
                        _uiState.update { state ->
                            state.copy(loginStatus = LoginStatus.SUCCESS)
                        }
                    }
                    .onFailure {
                        _uiState.update { state ->
                            state.copy(loginStatus = LoginStatus.FAILURE)
                        }
                    }
            } finally {
                isLoggingIn = false
            }
        }
    }

    fun onLoginResultHandled() {
        _uiState.update { currentState ->
            currentState.copy(loginStatus = LoginStatus.IDLE)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])

                LoginViewModel(
                    authRepository = AuthRepository(
                        authService = SearchApiFactory.authService,
                        localStorage = LocalStorage(application),
                    ),
                )
            }
        }
    }
}