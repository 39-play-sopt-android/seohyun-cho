package org.sopt.play

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.toArgb
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
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.sopt.play.ui.login.LoginScreen

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

        // 상단바 컬러 설정
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.White.toArgb(), // 라이트 모드 시 상태바 색상
                darkScrim = Color.DarkGray.toArgb() // 다크 모드 시 상태바 색상
            )
        )

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
