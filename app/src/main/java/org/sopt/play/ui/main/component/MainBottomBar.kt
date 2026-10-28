package org.sopt.play.ui.main.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

enum class MainTab(
    val label: String,
    @DrawableRes val iconRes: Int,
    @DrawableRes val selectedIconRes: Int,
) {
    HOME(
        label = "홈",
        iconRes = R.drawable.ic_home,
        selectedIconRes = R.drawable.ic_home_filled,
    ),
    FAVORITE(
        label = "찜",
        iconRes = R.drawable.favorite,
        selectedIconRes = R.drawable.favorite_fill,
    ),
    MY_PAGE(
        label = "마이페이지",
        iconRes = R.drawable.ic_user,
        selectedIconRes = R.drawable.ic_user_filled,
    ),
}

@Composable
fun MainBottomBar(
    selectedTab: MainTab,
    onTabClick: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .selectableGroup()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()

                drawLine(
                    color = Color(0xFFD1D5D6),
                    start = Offset(0f, strokeWidth / 2),
                    end = Offset(size.width, strokeWidth / 2),
                    strokeWidth = strokeWidth,
                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MainTab.entries.forEach { tab ->
            val selected = tab == selectedTab

            val color = if (selected) {
                Color(0xFF121212)
            } else {
                Color(0xFF505559)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp)
                    .selectable(
                        selected = selected,
                        role = Role.Tab,
                        onClick = {
                            onTabClick(tab)
                        },
                    )
                    .padding(vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 2.dp,
                    alignment = Alignment.CenterVertically,
                ),
            ) {
                Icon(
                    painter = painterResource(
                        if (selected) {
                            tab.selectedIconRes
                        } else {
                            tab.iconRes
                        }
                    ),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp),
                )

                Text(
                    text = tab.label,
                    fontFamily = FontFamily(
                        Font(R.font.pretendard_semibold)
                    ),
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    letterSpacing = (-0.14).sp,
                    color = color,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun MainBottomBarPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MainTab.entries.forEach { tab ->
            MainBottomBar(
                selectedTab = tab,
                onTabClick = {},
            )
        }
    }
}