package org.sopt.play.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

@Composable
fun HomeTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor =
        if (selected) Color(0xFF121212) else Color(0xFFB2BABD)

    Column(
        modifier = modifier
            .selectable(
                selected = selected,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = if (selected) {
                        Color(0xFF121212)
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape,
                ),
        )

        Text(
            text = text,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontSize = 18.sp,
            lineHeight = 21.6.sp,
            letterSpacing = (-0.18).sp,
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun HomeTabPreview() {
    Row {
        HomeTab(
            text = "2배적립",
            selected = false,
            onClick = {},
        )

        HomeTab(
            text = "홈",
            selected = true,
            onClick = {},
        )

        HomeTab(
            text = "셀렉티드",
            selected = false,
            onClick = {},
        )
    }
}