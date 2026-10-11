package org.sopt.play

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

    var nameFocused by remember { mutableStateOf(false) }
    var emailFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }
    var confirmPasswordFocused by remember { mutableStateOf(false) }

    //피드백 반영 (화면 modifier 적용 및 Figma 기준 여백 정리)
    Column(
        modifier =
            modifier
                .fillMaxSize()
                //.safeContentPadding()
                .verticalScroll(rememberScrollState())
                //피드백 반영 (회원가입 화면도 Figma 여백 좌우 16dp, 상단 60dp 적용)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 60.dp,
                )
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
            //피드백 반영 (Next로 다음 입력란 이동, 마지막 입력란은 Done 처리)
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
                    //피드백 반영 (Figma 테두리 2dp 적용, 오류와 포커스 색상 유지)
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
            //피드백 반영 (고정 높이 대신 내부 여백 사용, 입력 글자 잘림 방지 및 Pretendard 적용)
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
            //피드백 반영 (Next로 다음 입력란 이동, 마지막 입력란은 Done 처리)
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
                    //피드백 반영 (Figma 테두리 2dp 적용, 오류와 포커스 색상 유지)
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
            //피드백 반영 (고정 높이 대신 내부 여백 사용, 입력 글자 잘림 방지 및 Pretendard 적용)
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

        OutlinedTextField(
            state = passwordState,
            //피드백 반영 (Next로 다음 입력란 이동, 마지막 입력란은 Done 처리)
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
                    //피드백 반영 (Figma 테두리 2dp 적용, 오류와 포커스 색상 유지)
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
            //피드백 반영 (고정 높이 대신 내부 여백 사용, 입력 글자 잘림 방지 및 Pretendard 적용)
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

        OutlinedTextField(
            state = confirmPasswordState,
            //피드백 반영 (Next로 다음 입력란 이동, 마지막 입력란은 Done 처리)
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
                    //피드백 반영 (Figma 테두리 2dp 적용, 오류와 포커스 색상 유지)
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
            //피드백 반영 (고정 높이 대신 내부 여백 사용, 입력 글자 잘림 방지 및 Pretendard 적용)
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

        Button(
            onClick = { onRegister(email, password) },
            enabled = isRegisterEnabled,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            //피드백 반영 (둥근 버튼 의도를 CircleShape로 명확하게 표현)
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
                //피드백 반영 (빠진 텍스트에 Pretendard 적용)
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
            )
        }
    }
}

//피드백 반영 (Preview는 외부 호출이 없어 private으로 선언하고 파일 하단에 배치)
@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(onRegister = { _, _ -> })
}
