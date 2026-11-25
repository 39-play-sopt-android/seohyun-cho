package org.sopt.play.ui.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    // 시트가 열리고 닫히는 상태를 관리해.
    val sheetState = rememberModalBottomSheetState(
        // 반쯤 열린 중간 단계를 생략해.
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        // 사용자가 시트를 닫았을 때 호출할 함수.
        onDismissRequest = onDismissRequest,

        modifier = modifier,
        sheetState = sheetState,

        // 위쪽 모서리만 둥글게 설정.
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp,
        ),

        // 시트 배경색.
        containerColor = Color.White,
        tonalElevation = 0.dp,

        // 시트 위쪽의 회색 손잡이.
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                width = 32.dp,
                height = 4.dp,
                color = Color(0xFFB2BABD),
            )
        },
    ) {
        // 실제 내용을 넣는 영역.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp,
                ),
            content = content,
        )
    }
}