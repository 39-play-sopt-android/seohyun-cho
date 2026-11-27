package org.sopt.play.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    val round = RoundedCornerShape(12.dp)

    val Gray2 = Color(0xFFD1D5D6)

    val Gray5 = Color(0xFF505559)

    val borderLineColor = if (isFocused) Gray5 else Gray2

    val textStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        letterSpacing = (-0.18).sp,
    )

    val submitSearch: () -> Unit = {}

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .background(color = Color.White, shape = round)
            .clip(round)
            .border(
                width = 2.dp,
                color = borderLineColor,
                shape = round,
            )
            .padding(16.dp),
        singleLine = true,
        textStyle = textStyle,
        cursorBrush = SolidColor(Gray2),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        BasicText(
                            text = "검색어를 입력하세요",
                            style = textStyle.copy(
                                color = Gray2
                            ),
                        )
                    }

                    innerTextField()
                }

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFFB3BBBE),
                )
            }
        },
    )
}

@Preview(name = "기본 상태", widthDp = 328)
@Composable
private fun SearchTextFieldEmptyPreview() {
    var value by remember { mutableStateOf("") }

    SearchTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .focusProperties { canFocus = false },
    )
}

@Preview(name = "입력된 상태", widthDp = 328)
@Composable
private fun SearchTextFieldFilledPreview() {
    var value by remember { mutableStateOf("솝트") }

    SearchTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .focusProperties { canFocus = false },
    )
}

@Preview(name = "포커스 상태", widthDp = 328)
@Composable
private fun SearchTextFieldFocusedPreview() {
    var value by remember { mutableStateOf("솝트") }

    SearchTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .focusProperties { canFocus = true },
    )
}
