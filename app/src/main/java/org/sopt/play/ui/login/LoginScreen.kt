package org.sopt.play.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
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
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
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
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 60.dp,
                )
    ) {
        Text(
            text = "이메일로 로그인하기",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(32.dp))

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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
            ),
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
        Spacer(modifier = Modifier.height(32.dp))

        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            PlayButton(
                text = "로그인",
                enabled = uiState.isLoginEnabled,
                onClick = onLoginClick,

            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "아직 계정이 없으신가요?",
                fontSize = 14.sp,
                color = Color.Gray,

                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            )

            Spacer(modifier = Modifier.width(4.dp))

            TextButton(onClick = { onRegisterClick() }) {
                Text(
                    text = "회원가입하기",

                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,

                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                )
            }
        }
    }
}

@PlayScreenPreviews
@Composable
private fun LoginValidPreview() {
    LoginScreen(
        uiState = LoginUiState(
            email = "sopt@email.com",
            password = "password123",
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        onLoginClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun LoginEmailErrorPreview() {
    LoginScreen(
        uiState = LoginUiState(
            email = "잘못된 이메일",
            password = "password123",
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        onLoginClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun LoginPasswordErrorPreview() {
    LoginScreen(
        uiState = LoginUiState(
            email = "sopt@email.com",
            password = "123",
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        onLoginClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun LoginBothErrorsPreview() {
    LoginScreen(
        uiState = LoginUiState(
            email = "잘못된 이메일",
            password = "123",
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        onLoginClick = {},
    )
}

@PlayScreenPreviews
@Composable
private fun LoginEmptyPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onEmailChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        onLoginClick = {},
    )
}
