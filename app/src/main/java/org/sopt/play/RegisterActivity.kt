package org.sopt.play
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegisterScreen(
                onRegister = { email, password ->
                    val resultIntent = Intent().apply {
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

@Preview(showBackground = true)
@Composable
fun RegisterScreenPriview() {
    RegisterScreen(
        onRegister = { _, _ -> }
    )
}


@Composable
fun RegisterScreen(
    onRegister: (String, String) -> Unit
) {
    // 입력값 상태 관리
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // 이메일 형식 검사
    val isEmailError = email.isNotEmpty() &&
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    // 비밀번호 6자 이상 검사
    val isPasswordError = password.isNotEmpty() &&
            password.length < 6

    // 비밀번호 일치 검사
    val isConfirmPasswordError =
        confirmPassword.isNotEmpty() &&
                confirmPassword != password

    // 정보 미입력란(빈칸) 검사
    val isRegisterEnabled =
        name.isNotEmpty() &&
        email.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty() &&
                !isEmailError &&
                !isPasswordError &&
                !isConfirmPasswordError


    Column(
        modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(20.dp)
    ){
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "이메일로 회원가입",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.pretendard_bold))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "이름",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            placeholder = {
                Text("홍길동      ", color = Color(0xFFD1D5D6), fontFamily = FontFamily(Font(R.font.pretendard_medium)))
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

        Spacer(modifier = Modifier.height(24.dp))

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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "비밀번호 확인",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isError = isConfirmPasswordError,
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

        if (isConfirmPasswordError) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "비밀번호와 동일하게 입력해주세요.",
                fontSize = 12.sp,
                color = Color.Red,
                fontFamily = FontFamily(Font(R.font.pretendard_medium))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {onRegister(email, password)},
            enabled = isRegisterEnabled,
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
                text = "회원가입",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}