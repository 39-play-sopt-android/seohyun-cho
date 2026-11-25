package org.sopt.play.ui.search.component

import android.R.attr.thickness
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

@Composable
fun PostCard(
    author: String,
    date: String,
    title: String,
    content: String,
    modifier: Modifier = Modifier,
) {

    val Gray3 = Color(0xFFB2BABD)

    val Gray5 = Color(0xFF505559)

    val m12 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 14.4.sp,
        letterSpacing = (-0.12).sp,
    )

    val m16 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = (-0.16).sp,
    )

    val m14 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = (-0.14).sp,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF7F7F7))
            .padding(20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .height(IntrinsicSize.Min),
        ) {
            Text(text = author, style = m12, color = Gray3)

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 4.dp),
                thickness = 1.dp, // 선 두께
                color = Color.LightGray // 선 색상
            )

            Text(text = date, style = m12, color = Gray3)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = title, style = m16, color = Gray5)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = content, style = m14, color = Gray5)
    }
}

@Preview(widthDp = 360)
@Composable
private fun PostCardPreview() {
    PostCard(
        author = "응아니야",
        date = "2026.10.24",
        title = "글쓰기 테스트입니다 하하하하",
        content = "본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.본문입니다.",
        modifier = Modifier.padding(16.dp),
    )
}