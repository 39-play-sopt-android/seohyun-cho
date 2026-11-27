package org.sopt.play.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.component.PlayButton

val Gray6 = Color(0xFF23272A)

val m18 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 21.6.sp,
    letterSpacing = (-0.18).sp,
    textAlign = TextAlign.Center,
)

@Composable
fun PostModal(
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(131.dp)
            .width(288.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "게시글을 삭제할까요?",
            color = Gray6,
            style = m18,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            PlayButton(
                text = "취소",
                enabled = true,
                onClick = onCancel,
                fillWidth = false,
                containerColor = Gray1,
                contentColor = Gray5,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(12.dp))

            PlayButton(
                text = "삭제",
                enabled = true,
                onClick = onDelete,
                fillWidth = false,
                containerColor = Color.Black,
                contentColor = Color.White,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview
@Composable
fun PostModalPreview() {
    PostModal(
        onCancel = {},
        onDelete = {},
    )
}