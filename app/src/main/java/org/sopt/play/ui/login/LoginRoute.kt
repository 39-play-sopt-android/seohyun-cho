package org.sopt.play.ui.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    registeredEmail: String,
    registeredPassword: String,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(registeredEmail, registeredPassword) {
        viewModel.setRegisteredCredentials(
            email = registeredEmail,
            password = registeredPassword,
        )
    }

    LoginScreen(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRegisterClick = onRegisterClick,
        onLoginClick = {
            val isSuccess = viewModel.login(
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword,
            )

            if (isSuccess) {
                onLoginClick()
            } else {
                Toast.makeText(
                    context,
                    "로그인에 실패했습니다.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        },
    )
}