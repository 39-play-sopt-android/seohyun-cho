package org.sopt.play.ui.preview

import androidx.compose.ui.tooling.preview.Preview

// 커스텀 어노테이션
@Preview(
    name = "기본 글자",
    showBackground = true,
    widthDp = 360,
    heightDp = 724,
    fontScale = 1f,
)
@Preview(
    name = "큰 글자",
    showBackground = true,
    widthDp = 360,
    heightDp = 724,
    fontScale = 1.5f,
)
annotation class PlayScreenPreviews
