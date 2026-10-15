package org.sopt.play.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

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
