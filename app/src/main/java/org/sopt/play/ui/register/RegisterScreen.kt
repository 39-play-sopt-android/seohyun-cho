package org.sopt.play.ui.register

import android.util.Patterns
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.component.PlayButton
import org.sopt.play.ui.component.PlayTextField

@Composable
fun RegisterScreen(
    //피드백 반영 (회원가입 화면에도 외부 modifier 전달 지원)
    modifier: Modifier = Modifier,
    onRegister: (String, String) -> Unit,
) {
    //피드백 반영 (내부 여백을 지원하는 State 기반 TextField로 입력 상태 관리)
    // 입력값 상태 관리
    val nameState = rememberTextFieldState()
    val name = nameState.text.toString()
    val emailState = rememberTextFieldState()
    val email = emailState.text.toString()
    val passwordState = rememberTextFieldState()
    val password = passwordState.text.toString()
    val confirmPasswordState = rememberTextFieldState()
    val confirmPassword = confirmPasswordState.text.toString()

    // 이메일 형식 검사
    val isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    // 비밀번호 6자 이상 검사
    val isPasswordError = password.isNotEmpty() && password.length < 6

    // 비밀번호 일치 검사
    val isConfirmPasswordError = confirmPassword.isNotEmpty() && confirmPassword != password

    // 정보 미입력란(빈칸) 검사
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


    //피드백 반영 (화면 modifier 적용 및 Figma 기준 여백 정리)
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
            state = nameState,
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "비밀번호 확인",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        )

        Spacer(modifier = Modifier.height(8.dp))

        PlayTextField(
            state = confirmPasswordState,
            placeholder = "6자 이상의 비밀번호",
            isError = isConfirmPasswordError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
            visible = isConfirmPasswordError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            //피드백 반영 (오류 문구와 여백이 겹치지 않도록 세로 배치)
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
            enabled = isRegisterEnabled,
            onClick = {
                onRegister(email, password)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(onRegister = { _, _ -> })
}
