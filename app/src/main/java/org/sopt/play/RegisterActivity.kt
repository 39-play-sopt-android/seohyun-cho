package org.sopt.play

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.White.toArgb(),
                darkScrim = Color.DarkGray.toArgb()
            )
        )

        setContent {
            RegisterScreen(
                onRegister = { email, password ->
                    val resultIntent =
                        Intent().apply {
                            putExtra("email", email)
                            putExtra("password", password)
                        }

                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun RegisterScreen(

    modifier: Modifier = Modifier,
    onRegister: (String, String) -> Unit,
) {

    val nameState = rememberTextFieldState()
    val name = nameState.text.toString()
    val emailState = rememberTextFieldState()
    val email = emailState.text.toString()
    val passwordState = rememberTextFieldState()
    val password = passwordState.text.toString()
    val confirmPasswordState = rememberTextFieldState()
    val confirmPassword = confirmPasswordState.text.toString()

    val isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError = password.isNotEmpty() && password.length < 6

    val isConfirmPasswordError = confirmPassword.isNotEmpty() && confirmPassword != password

    val isRegisterEnabled =
        name.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            !isEmailError &&
            !isPasswordError &&
            !isConfirmPasswordError

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var nameFocused by remember { mutableStateOf(false) }
    var emailFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }
    var confirmPasswordFocused by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .safeDrawingPadding()
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

        OutlinedTextField(
            state = nameState,

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            placeholder = {
                Text(
                    "홍길동",
                    color = Color(0xFFD1D5D6),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            modifier =
                Modifier.fillMaxWidth()
                    .onFocusChanged { nameFocused = it.isFocused }

                    .border(
                        width = 2.dp,
                        color =
                            when {
                                false -> Color.Red
                                nameFocused -> Color(0xFF505559)
                                else -> Color(0xFFD1D5D6)
                            },
                        shape = RoundedCornerShape(12.dp),
                    ),

            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
            textStyle =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
            shape = RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "이메일 주소",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            state = emailState,

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = isEmailError,
            placeholder = {
                Text(
                    "abc@email.com",
                    color = Color(0xFFD1D5D6),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            modifier =
                Modifier.fillMaxWidth()
                    .onFocusChanged { emailFocused = it.isFocused }

                    .border(
                        width = 2.dp,
                        color =
                            when {
                                isEmailError -> Color.Red
                                emailFocused -> Color(0xFF505559)
                                else -> Color(0xFFD1D5D6)
                            },
                        shape = RoundedCornerShape(12.dp),
                    ),

            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
            textStyle =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
            shape = RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
        )

        AnimatedVisibility(
            visible = isEmailError,
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

        OutlinedTextField(
            state = passwordState,

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = isPasswordError,
            placeholder = {
                Text(
                    "6자 이상의 비밀번호",
                    color = Color(0xFFD1D5D6),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            outputTransformation =
                OutputTransformation {
                    replace(0, length, "•".repeat(length))
                },
            modifier =
                Modifier.fillMaxWidth()
                    .onFocusChanged { passwordFocused = it.isFocused }

                    .border(
                        width = 2.dp,
                        color =
                            when {
                                isPasswordError -> Color.Red
                                passwordFocused -> Color(0xFF505559)
                                else -> Color(0xFFD1D5D6)
                            },
                        shape = RoundedCornerShape(12.dp),
                    ),

            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
            textStyle =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
            shape = RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
        )

        AnimatedVisibility(
            visible = isPasswordError,
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

        OutlinedTextField(
            state = confirmPasswordState,

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onKeyboardAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            isError = isConfirmPasswordError,
            placeholder = {
                Text(
                    "6자 이상의 비밀번호",
                    color = Color(0xFFD1D5D6),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            outputTransformation =
                OutputTransformation {
                    replace(0, length, "•".repeat(length))
                },
            modifier =
                Modifier.fillMaxWidth()
                    .onFocusChanged { confirmPasswordFocused = it.isFocused }

                    .border(
                        width = 2.dp,
                        color =
                            when {
                                isConfirmPasswordError -> Color.Red
                                confirmPasswordFocused -> Color(0xFF505559)
                                else -> Color(0xFFD1D5D6)
                            },
                        shape = RoundedCornerShape(12.dp),
                    ),

            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
            textStyle =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
            shape = RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
        )

        AnimatedVisibility(
            visible = isConfirmPasswordError,
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

        Button(
            onClick = { onRegister(email, password) },
            enabled = isRegisterEnabled,
            modifier = Modifier.fillMaxWidth().height(54.dp),

            shape = CircleShape,
            colors =
                ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color(0xFFB2BABD),
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
        ) {
            Text(
                text = "회원가입",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,

                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(onRegister = { _, _ -> })
}
