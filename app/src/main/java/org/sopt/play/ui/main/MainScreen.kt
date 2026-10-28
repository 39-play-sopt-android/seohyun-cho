package org.sopt.play.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import org.sopt.play.ui.preview.PlayScreenPreviews
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.home.HomeUiState
import org.sopt.play.ui.home.HomeScreen
import org.sopt.play.ui.main.component.MainBottomBar
import org.sopt.play.ui.main.component.MainTab

@Composable
fun MainScreen(
    userName: String,
    modifier: Modifier = Modifier,
    initialTab: MainTab = MainTab.HOME,
    homeUiState: HomeUiState = HomeUiState(),
    onMenuClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = {},
) {
    var selectedTab by rememberSaveable {
        mutableStateOf(initialTab)
    }

    val stateHolder = rememberSaveableStateHolder()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            stateHolder.SaveableStateProvider(
                key = selectedTab.name,
            ) {
                when (selectedTab) {
                    MainTab.HOME -> HomeScreen(
                        userName = userName,
                        uiState = homeUiState,
                        onMenuClick = onMenuClick,
                        onCategoryClick = onCategoryClick,
                        onFavoriteClick = onFavoriteClick,
                    )

                    MainTab.FAVORITE -> MainPlaceholderScreen(
                        title = "찜",
                    )

                    MainTab.MY_PAGE -> MainPlaceholderScreen(
                        title = "마이페이지",
                    )
                }
            }
        }

        MainBottomBar(
            selectedTab = selectedTab,
            onTabClick = { tab ->
                selectedTab = tab
            },
        )
    }
}

@Composable
private fun MainPlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            fontFamily = FontFamily(
                Font(R.font.pretendard_medium)
            ),
            fontSize = 18.sp,
            lineHeight = 21.6.sp,
            color = Color(0xFF121212),
        )
    }
}

@PlayScreenPreviews
@Composable
private fun MainScreenPreview() {
    MainScreen(userName = "김솝트")
}

@PlayScreenPreviews
@Composable
private fun MainFavoriteScreenPreview() {
    MainScreen(
        userName = "김솝트",
        initialTab = MainTab.FAVORITE,
    )
}

@PlayScreenPreviews
@Composable
private fun MainMyPageScreenPreview() {
    MainScreen(
        userName = "김솝트",
        initialTab = MainTab.MY_PAGE,
    )
}