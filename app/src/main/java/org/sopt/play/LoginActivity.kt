package org.sopt.play

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    //피드백 반영 (가입 정보 저장은 일반 변수로 유지하고 nullable 전달 오류 방지)
    private var registeredEmail: String = ""
    private var registeredPassword: String = ""

    private val registerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                registeredEmail = result.data?.getStringExtra("email") ?: ""
                registeredPassword = result.data?.getStringExtra("password") ?: ""
                registerSuccessEvents.trySend(registeredEmail to registeredPassword)
            }
        }

    //피드백 반영 (가입 완료는 UI 상태 대신 일회성 이벤트로 전달)
    private val registerSuccessEvents = Channel<Pair<String, String>>(Channel.BUFFERED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            //피드백 반영 (일반 저장 변수와 구분하여 입력란 갱신에 필요한 화면 상태만 관리)
            var loginCredentials by remember { mutableStateOf(registeredEmail to registeredPassword) }

            //피드백 반영 (상태 플래그 없이 가입 완료 알림을 수신하고 자동으로 닫기)
            LaunchedEffect(Unit) {
                registerSuccessEvents.receiveAsFlow().collect { credentials ->
                    loginCredentials = credentials
                    launch {
                        snackbarHostState.showSnackbar(
                            message = "회원가입이 완료되었어요!",
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState) { data ->
                        //피드백 반영 (배경색과 둥근 모서리를 제공하는 Surface 유지, 단순 배치용 Box와 구분)
                        Surface(
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                text = data.visuals.message,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp),
                                //피드백 반영 (빠진 텍스트에 Pretendard 적용)
                                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            )
                        }
                    }
                }
            ) { innerPadding ->
                //피드백 반영 (중복 Box 제거 후 화면 modifier에 Scaffold 여백 전달)
                LoginScreen(
                    registeredEmail = loginCredentials.first,
                    registeredPassword = loginCredentials.second,
                    modifier = Modifier.padding(innerPadding),
                    onRegisterClick = {
                        registerLauncher.launch(
                            Intent(this@LoginActivity, RegisterActivity::class.java)
                        )
                    },
                    onLoginClick = {
                        val intent =
                            Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java,
                                )
                                .apply {
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }

                        startActivity(intent)
                    },
                )
            }
        }
    }
}

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

    var emailFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }

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

        OutlinedTextField(
            // value = email,
            // onValueChange = {email = it},
            state = emailState,
            isError = isEmailError,
            placeholder = {
                Text(
                    "abc@email.com",
                    color = Color(0xFFD1D5D6),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            },
            // singleLine = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            //피드백 반영 (Next 키는 기본 포커스 이동 동작을 사용)
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            /*keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            ),*/
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
            // value = password,
            // onValueChange = { password = it },
            state = passwordState,
            isError = isPasswordError,
            placeholder = {
                Text(
                    "6자 이상의 비밀번호",
                    color = Color(0xFFD1D5D6),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                )
            },
            // singleLine = true,

            onKeyboardAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            /*keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),*/
            // visualTransformation = PasswordVisualTransformation(),
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
        Spacer(modifier = Modifier.height(32.dp))

        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            Button(
                onClick = {
                    if (
                        email == registeredEmail &&
                            password == registeredPassword &&
                            registeredEmail.isNotEmpty() &&
                            registeredPassword.isNotEmpty()
                    ) {
                        onLoginClick()
                    } else {
                        Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = isLoginEnabled,
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
                    text = "로그인",
                    //피드백 반영 (버튼 글자를 Figma 기준 14sp, SemiBold로 조정)
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    //피드백 반영 (빠진 텍스트에 Pretendard 적용)
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                )
            }
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

//피드백 반영 (Preview는 외부 호출이 없어 private으로 선언하고 파일 하단에 배치)
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
