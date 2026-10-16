package org.sopt.play.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.preview.PlayScreenPreviews

@Composable
fun PlayTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    outputTransformation: OutputTransformation? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(12.dp)
    val borderColor = when {
        isError -> Color.Red
        isFocused -> Color(0xFF505559)
        else -> Color(0xFFD1D5D6)
    }

    OutlinedTextField(
        state = state,
        isError = isError,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFD1D5D6),
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            )
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        outputTransformation = outputTransformation,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(width = 2.dp, color = borderColor, shape = shape),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
    )
}

@PlayScreenPreviews
@Composable
private fun PlayTextFieldPreview() {
    val emptyState = rememberTextFieldState()

    val filledState = rememberTextFieldState(
        initialText = "sopt@email.com",
    )

    val errorState = rememberTextFieldState(
        initialText = "잘못된 이메일",
    )

    val focusedState = rememberTextFieldState(
        initialText = "입력 중",
    )

    val passwordState = rememberTextFieldState(
        initialText = "password123",
    )

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "기본 · 비어 있음")

        PlayTextField(
            state = emptyState,
            placeholder = "abc@email.com",
        )

        Text(text = "입력 완료")

        PlayTextField(
            state = filledState,
            placeholder = "abc@email.com",
        )

        Text(text = "오류")

        PlayTextField(
            state = errorState,
            placeholder = "abc@email.com",
            isError = true,
        )

        Text(text = "포커스")

        PlayTextField(
            state = focusedState,
            placeholder = "내용을 입력해주세요",
            modifier = Modifier.focusRequester(focusRequester),
        )

        Text(text = "비밀번호")

        PlayTextField(
            state = passwordState,
            placeholder = "6자 이상의 비밀번호",
            outputTransformation = OutputTransformation {
                replace(0, length, "•".repeat(length))
            },
        )
    }
}
