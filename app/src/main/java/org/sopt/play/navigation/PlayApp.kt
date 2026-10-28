package org.sopt.play.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.sopt.play.ui.main.MainScreen
import org.sopt.play.R
import org.sopt.play.ui.login.LoginScreen
import org.sopt.play.ui.register.RegisterScreen

private data class RegisteredUser(
    val name: String = "",
    val email: String = "",
    val password: String = "",
)

@Composable
fun PlayApp() {
    val navController = rememberNavController()

    var registeredUser by remember { mutableStateOf(RegisteredUser()) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Surface(
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = data.visuals.message,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp),
                        fontFamily = FontFamily(
                            Font(R.font.pretendard_medium)
                        ),
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        ) {
            composable(route = "login") {
                LoginScreen(
                    registeredEmail = registeredUser.email,
                    registeredPassword = registeredUser.password,
                    onRegisterClick = {
                        navController.navigate("register") {
                            launchSingleTop = true
                        }
                    },
                    onLoginClick = {
                        navController.navigate("home") {
                            popUpTo("login") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(route = "register") {
                RegisterScreen(
                    onRegister = { name, email, password ->
                        registeredUser = RegisteredUser(name, email, password)

                        navController.popBackStack()

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "회원가입이 완료되었어요!",
                                duration = SnackbarDuration.Short,
                            )
                        }
                    },
                )
            }

            composable(route = "home") {
                MainScreen(userName = registeredUser.name)
            }
        }
    }
}