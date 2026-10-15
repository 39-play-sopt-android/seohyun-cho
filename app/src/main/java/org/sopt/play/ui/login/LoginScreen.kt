package org.sopt.play.ui.login

import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import org.sopt.play.R
import org.sopt.play.ui.component.PlayButton
import org.sopt.play.ui.component.PlayTextField

@Composable
fun LoginScreen(
    registeredEmail: String,
    registeredPassword: String,
    //피드백 반영 (호출하는 쪽에서 화면 여백을 조절할 수 있도록 modifier 제공)
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {

    /*var email by remember(registeredEmail) {
        mutableStateOf(registeredEmail)
    }
    var password by remember(registeredPassword) {
        mutableStateOf(registeredPassword)
    }*/

    //피드백 반영 (회원가입 정보로 로그인 입력란을 자동 채우기 위한 초기값)
    val emailState = rememberTextFieldState(initialText = registeredEmail)

    val passwordState = rememberTextFieldState(initialText = registeredPassword)

    //피드백 반영 (remember 초기값은 재적용되지 않으므로 가입 정보 변경 시 입력값 갱신)
    LaunchedEffect(registeredEmail, registeredPassword) {
        emailState.edit {
            replace(0, length, registeredEmail)
        }

        passwordState.edit {
            replace(0, length, registeredPassword)
        }
    }

    val email = emailState.text.toString()
    val password = passwordState.text.toString()

    val isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError = password.isNotEmpty() && password.length < 6

    val isLoginEnabled =
        email.isNotEmpty() && password.isNotEmpty() && !isEmailError && !isPasswordError

    // 심화과제
    //피드백 반영 (Next의 기본 포커스 이동을 사용하고 명시적인 FocusRequester 제거)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // 도약과제
    val context = LocalContext.current

    //피드백 반영 (화면 modifier 적용 및 Figma 기준 여백 정리)
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.White)
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
            state = emailState,
            placeholder = "abc@email.com",
            isError = isEmailError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        //피드백 반영 (visible로 오류 표시 제어, 중복 if 제거 및 글자 크기 통일)
        AnimatedVisibility(
            visible = isEmailError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            //피드백 반영 (오류 문구와 여백이 겹치지 않도록 세로 배치)
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
            state = passwordState,
            placeholder = "6자 이상의 비밀번호",
            isError = isPasswordError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            onKeyboardAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            outputTransformation = OutputTransformation {
                replace(0, length, "•".repeat(length))
            },
        )

        //피드백 반영 (visible로 오류 표시 제어, 중복 if 제거 및 글자 크기 통일)
        AnimatedVisibility(
            visible = isPasswordError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            //피드백 반영 (오류 문구와 여백이 겹치지 않도록 세로 배치)
            Column {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "비밀번호는 6자 이상 입력해주세요.",
                    fontSize = 14.sp,
                    color = Color.Red,
                    //피드백 반영 (빠진 텍스트에 Pretendard 적용)
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // 공통 컴포넌트로 변경
        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            PlayButton(
                text = "로그인",
                enabled = isLoginEnabled,
                onClick = {
                    if (
                        email == registeredEmail &&
                        password == registeredPassword &&
                        registeredEmail.isNotEmpty() &&
                        registeredPassword.isNotEmpty()
                    ) {
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
                //피드백 반영 (빠진 텍스트에 Pretendard 적용)
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            )

            Spacer(modifier = Modifier.width(4.dp))

            TextButton(onClick = { onRegisterClick() }) {
                Text(
                    text = "회원가입하기",
                    //피드백 반영 (버튼 글자를 Figma 기준 14sp, SemiBold로 조정)
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    //피드백 반영 (빠진 텍스트에 Pretendard 적용)
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        registeredEmail = "",
        registeredPassword = "",
        onRegisterClick = {},
        onLoginClick = {},
    )
}