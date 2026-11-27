package org.sopt.play.ui.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.component.PlayButton
import org.sopt.play.ui.component.PlayTextField

val Gray1 = Color(0xFFF7F7F7)
val Gray3 = Color(0xFFB2BABD)
val Gray5 = Color(0xFF505559)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    onComplete: (String, String) -> Unit,
    title: String = "",
    body: String = "",
) {

    val sheetState = rememberModalBottomSheetState()

            ModalBottomSheet(
                onDismissRequest = onDismissRequest,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                ),
                containerColor = Color.White,
                dragHandle = {
                    BottomSheetDefaults.DragHandle(
                        color = Gray3,
                        width = 32.dp,
                        height = 4.dp,
                    )
                },
                sheetState = sheetState
            ) {
                PostBottomSheetContent(
                    onComplete = onComplete,
                    title = title,
                    body = body,
                )


    }
}

@Composable
fun PostBottomSheetContent(
    onComplete: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    body: String = "",
) {
    var title by rememberSaveable(title) { mutableStateOf(title) }
    var body by rememberSaveable(body) { mutableStateOf(body) }

    val isCompleteEnabled = title.isNotBlank() && body.isNotBlank()

    val sb16 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = (-0.16).sp,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "제목",
            style = sb16,
            modifier = Modifier.padding(start = 8.dp),
        )

        Spacer(modifier = Modifier.height(6.dp))

        PlayTextField(
            value = title,
            onValueChange = {
                if (it.length <= 20) {
                    title = it
                }
            },
            placeholder = "제목을 입력하세요.",
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "본문",
            style = sb16,
            modifier = Modifier.padding(start = 8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        PlayTextField(
            value = body,
            onValueChange = {
                if (it.length <= 1000) {
                    body = it
                }
            },
            placeholder = "본문을 입력하세요.",
        )

        Spacer(modifier = Modifier.height(20.dp))

        PlayButton(
            text = "완료",
            enabled = isCompleteEnabled,
            onClick = {onComplete(title, body)},
            containerColor = Gray1,
            contentColor = Gray5,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetNonePreview() {
    PostBottomSheetContent(
        onComplete = { _, _ -> },
    )
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetTruePreview() {
    PostBottomSheetContent(
        onComplete = { _, _ -> }, title = "제목입니다", body = "본문입니다."
    )
}
