package org.sopt.play.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.preview.PlayScreenPreviews

@Composable
fun PlayTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var isFocused by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(12.dp)
    val borderColor = when {
        isError -> Color.Red
        isFocused -> Color(0xFF505559)
        else -> Color(0xFFD1D5D6)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFD1D5D6),
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            )
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(width = 2.dp, color = borderColor, shape = shape),
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
    val emptyState = remember { mutableStateOf("") }

    val filledState = remember { mutableStateOf("sopt@email.com") }

    val errorState = remember { mutableStateOf("잘못된 이메일") }

    val focusedState = remember { mutableStateOf("입력 중") }

    val passwordState = remember { mutableStateOf("password123") }

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
            value = emptyState.value,
            onValueChange = { emptyState.value = it },
            placeholder = "abc@email.com",
        )

        Text(text = "입력 완료")

        PlayTextField(
            value = filledState.value,
            onValueChange = { filledState.value = it },
            placeholder = "abc@email.com",
        )

        Text(text = "오류")

        PlayTextField(
            value = errorState.value,
            onValueChange = { errorState.value = it },
            placeholder = "abc@email.com",
            isError = true,
        )

        Text(text = "포커스")

        PlayTextField(
            value = focusedState.value,
            onValueChange = { focusedState.value = it },
            placeholder = "내용을 입력해주세요",
            modifier = Modifier.focusRequester(focusRequester),
        )

        Text(text = "비밀번호")

        PlayTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            placeholder = "6자 이상의 비밀번호",
            visualTransformation = PasswordVisualTransformation(),
        )
    }
}
