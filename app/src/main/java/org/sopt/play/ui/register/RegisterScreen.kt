package org.sopt.play.ui.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.component.PlayButton
import org.sopt.play.ui.component.PlayTextField
import org.sopt.play.ui.preview.PlayScreenPreviews

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.White)
                .safeDrawingPadding() //.safeContentPadding() -> 얘랑 다름
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 60.dp)

    ) {
        Text(
            text = "이메일로 회원가입",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "이름",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        PlayTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            placeholder = "홍길동",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "이메일 주소",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        PlayTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            placeholder = "abc@email.com",
            isError = uiState.isEmailError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        AnimatedVisibility(
            visible = uiState.isEmailError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {

            Column {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "올바른 이메일을 입력해주세요.",
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontSize = 14.sp,
                    color = Color.Red,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "비밀번호",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        )

        Spacer(modifier = Modifier.height(8.dp))

        PlayTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            placeholder = "6자 이상의 비밀번호",
            isError = uiState.isPasswordError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
        )

        AnimatedVisibility(
            visible = uiState.isPasswordError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {

            Column {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "비밀번호는 6자 이상 입력해주세요.",
                    fontSize = 14.sp,
                    color = Color.Red,

                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "비밀번호 확인",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        )

        Spacer(modifier = Modifier.height(8.dp))

        PlayTextField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "6자 이상의 비밀번호",
            isError = uiState.isConfirmPasswordError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
            ),
            visualTransformation = PasswordVisualTransformation(),
        )

        AnimatedVisibility(
            visible = uiState.isConfirmPasswordError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {

            Column {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "비밀번호와 동일하게 입력해주세요.",
                    fontSize = 14.sp,
                    color = Color.Red,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        PlayButton(
            text = "회원가입",
            enabled = uiState.isRegisterEnabled,
            onClick = onRegisterClick,
        )
    }
}

@PlayScreenPreviews
@Composable
private fun RegisterValidPreview() {
    RegisterScreen(
        uiState = RegisterUiState(
            name = "홍길동",
            email = "qwer1234@gmail.com",
            password = "qwer1234",
            confirmPassword = "qwer1234",
        ),
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun RegisterEmptyPreview() {
    RegisterScreen(
        uiState = RegisterUiState(),
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun RegisterEmailErrorPreview() {
    RegisterScreen(
        uiState = RegisterUiState(
            name = "홍길동",
            email = "잘못된 이메일",
            password = "password123",
            confirmPassword = "password123",
        ),
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun RegisterPasswordErrorPreview() {
    RegisterScreen(
        uiState = RegisterUiState(
            name = "홍길동",
            email = "sopt@email.com",
            password = "123",
            confirmPassword = "123",
        ),
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun RegisterMismatchPreview() {
    RegisterScreen(
        uiState = RegisterUiState(
            name = "홍길동",
            email = "sopt@email.com",
            password = "password123",
            confirmPassword = "different123",
        ),
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun RegisterAllErrorsPreview() {
    RegisterScreen(
        uiState = RegisterUiState(
            name = "홍길동",
            email = "잘못된 이메일",
            password = "123",
            confirmPassword = "456",
        ),
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
    )
}
