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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private var registeredEmail by mutableStateOf("")
    private var registeredPassword by mutableStateOf("")

    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            registeredEmail = result.data?.getStringExtra("email") ?: ""
            registeredPassword = result.data?.getStringExtra("password") ?: ""
            showRegisterSuccess = true
        }
    }

    private var showRegisterSuccess by mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(showRegisterSuccess) {
                if (showRegisterSuccess) {
                    launch {
                        snackbarHostState.showSnackbar(
                            message = "회원가입이 완료되었어요!",
                            duration = SnackbarDuration.Indefinite
                        )
                    }

                    delay(3000)
                    snackbarHostState.currentSnackbarData?.dismiss()
                    showRegisterSuccess = false
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState) { data ->
                        Surface(
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = data.visuals.message,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {

            LoginScreen(
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword,

                onRegisterClick = {
                    registerLauncher.launch(
                        Intent(this@LoginActivity, RegisterActivity::class.java)
                    )
                },

                onLoginClick = {
                    val intent = Intent(
                        this@LoginActivity,
                        MainActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)
                }


            )}

            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        registeredEmail = "",
        registeredPassword = "",
        onRegisterClick = {},
        onLoginClick = {}
    )
}

@Composable
fun LoginScreen(
    registeredEmail: String,
    registeredPassword: String,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
){

    /*var email by remember(registeredEmail) {
        mutableStateOf(registeredEmail)
    }
    var password by remember(registeredPassword) {
        mutableStateOf(registeredPassword)
    }*/

    val emailState = rememberTextFieldState(
        initialText = registeredEmail
    )

    val passwordState = rememberTextFieldState(
        initialText = registeredPassword
    )

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


    val isEmailError = email.isNotEmpty() &&
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError = password.isNotEmpty() && password.length < 6

    val isLoginEnabled =
        email.isNotEmpty() && password.isNotEmpty() && !isEmailError && !isPasswordError

    // 심화과제
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // 도약과제
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp
            )
    ){
        Text(text = "이메일로 로그인하기",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
            )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "이메일 주소",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            //value = email,
            //onValueChange = {email = it},
            state = emailState,
            isError = isEmailError,
            placeholder = {
                Text("abc@email.com", color = Color(0xFFD1D5D6), fontFamily = FontFamily(Font(R.font.pretendard_medium)))
            },
            //singleLine = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            onKeyboardAction = {
                passwordFocusRequester.requestFocus()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            /*keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            ),*/
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD1D5D6),
                focusedBorderColor = Color(0xFF505559),
                errorBorderColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        AnimatedVisibility(
            visible = isEmailError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ){
        if (isEmailError) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "올바른 이메일을 입력해주세요.",
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontSize = 12.sp,
                color = Color.Red
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
            //value = password,
            //onValueChange = { password = it },
            state = passwordState,
            isError = isPasswordError,
            placeholder = {
                Text("6자 이상의 비밀번호", color = Color(0xFFD1D5D6), fontFamily = FontFamily(Font(R.font.pretendard_medium)))
            },
            //singleLine = true,

            onKeyboardAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            /*keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),*/
            //visualTransformation = PasswordVisualTransformation(),
            outputTransformation = OutputTransformation {
                replace(0, length, "•".repeat(length))
            },
            modifier = Modifier
                .focusRequester(passwordFocusRequester)
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD1D5D6),
                focusedBorderColor = Color(0xFF505559),
                errorBorderColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        AnimatedVisibility(
            visible = isPasswordError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        )
        {
            if (isPasswordError) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "비밀번호는 6자 이상 입력해주세요.",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))


        CompositionLocalProvider(
            LocalRippleConfiguration provides null
        )
        {
            Button(
                onClick = {
                    if (email == registeredEmail &&
                        password == registeredPassword &&
                        registeredEmail.isNotEmpty() &&
                        registeredPassword.isNotEmpty()
                    ) {
                        onLoginClick()
                    } else {
                        Toast.makeText(context,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = isLoginEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color(0xFFB2BABD),
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) {
                Text(
                    text = "로그인",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "아직 계정이 없으신가요?",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.width(4.dp))

            TextButton(
                onClick = {onRegisterClick()}
            ) {
                Text(
                    text = "회원가입하기",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

    }

}