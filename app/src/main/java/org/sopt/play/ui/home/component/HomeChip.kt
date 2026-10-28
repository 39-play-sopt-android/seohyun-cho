package org.sopt.play.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

@Composable
fun HomeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(6.dp)

    val backgroundColor =
        if (selected) Color(0xFF505559) else Color.White

    val borderColor =
        if (selected) Color(0xFF505559) else Color(0xFFD1D5D6)

    val textColor =
        if (selected) Color.White else Color(0xFF505559)

    Text(
        text = text,
        color = textColor,
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = (-0.14).sp,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(1.dp, borderColor, shape)
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
    )
}

@Preview(
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun HomeChipPreview() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        HomeChip(
            text = "전체",
            selected = true,
            onClick = {},
        )

        HomeChip(
            text = "상의",
            selected = false,
            onClick = {},
        )

        HomeChip(
            text = "팬츠",
            selected = false,
            onClick = {},
        )
    }
}