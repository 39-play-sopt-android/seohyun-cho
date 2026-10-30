package org.sopt.play.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterRoute(
    onRegister: (String, String, String) -> Unit,
    viewModel: RegisterViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = {
            viewModel.register()?.let { state ->
                onRegister(state.name, state.email, state.password)
            }
        },
    )
}
