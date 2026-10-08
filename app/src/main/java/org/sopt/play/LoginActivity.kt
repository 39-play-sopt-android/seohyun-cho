package org.sopt.play
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {

    private var registeredEmail by mutableStateOf("")
    private var registeredPassword by mutableStateOf("")

    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            registeredEmail = result.data?.getStringExtra("email") ?: ""
            registeredPassword = result.data?.getStringExtra("password") ?: ""
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen(
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword,
                onRegisterClick = {
                    registerLauncher.launch(
                        Intent(this, RegisterActivity::class.java)
                    )
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        registeredEmail = "",
        registeredPassword = "",
        onRegisterClick = {}
    )
}

@Composable
fun LoginScreen(
    registeredEmail: String,
    registeredPassword: String,
    onRegisterClick: () -> Unit
){

    var email by remember(registeredEmail) {
        mutableStateOf(registeredEmail)
    }
    var password by remember(registeredPassword) {
        mutableStateOf(registeredPassword)
    }


    val isEmailError = email.isNotEmpty() &&
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError = password.isNotEmpty() && password.length < 6

    val isLoginEnabled =
        email.isNotEmpty() && password.isNotEmpty() && !isEmailError && !isPasswordError

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
            value = email,
            onValueChange = {email = it},
            isError = isEmailError,
            placeholder = {
                Text("abc@email.com", color = Color(0xFFD1D5D6), fontFamily = FontFamily(Font(R.font.pretendard_medium)))
            },
            singleLine = true,
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

        if (isEmailError) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "올바른 이메일을 입력해주세요.",
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontSize = 12.sp,
                color = Color.Red
            )
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
            value = password,
            onValueChange = { password = it },
            isError = isPasswordError,
            placeholder = {
                Text("6자 이상의 비밀번호", color = Color(0xFFD1D5D6), fontFamily = FontFamily(Font(R.font.pretendard_medium)))
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
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

        if (isPasswordError) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "비밀번호는 6자 이상 입력해주세요.",
                fontSize = 12.sp,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { },
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