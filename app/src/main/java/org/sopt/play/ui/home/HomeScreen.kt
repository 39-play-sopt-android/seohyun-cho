package org.sopt.play.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.home.component.HomeBanner
import org.sopt.play.ui.home.component.HomeChip
import org.sopt.play.ui.home.component.HomeTab
import org.sopt.play.ui.home.component.HomeTopBar
import org.sopt.play.ui.home.component.ProductCard
import org.sopt.play.ui.preview.PlayScreenPreviews

@Composable
fun HomeScreen(
    userName: String,
    uiState: HomeUiState,
    onMenuClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        HomeTopBar(
            onSearchClick = {},
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 96.dp),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) },
            ) {
                Column {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(
                            items = uiState.menus,
                            key = { it },
                        ) { menu ->
                            HomeTab(
                                text = menu,
                                selected = menu == uiState.selectedMenu,
                                onClick = {
                                    onMenuClick(menu)
                                },
                            )
                        }
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            items = uiState.banners,
                            key = { it },
                        ) { banner ->
                            HomeBanner(
                                imageRes = banner,
                                contentDescription = "기획전 배너",
                                modifier = Modifier.width(328.dp),
                            )
                        }
                    }

                    Text(
                        text = "${userName}님을 위한 추천 아이템",
                        fontFamily = FontFamily(
                            Font(R.font.pretendard_medium)
                        ),
                        fontSize = 18.sp,
                        lineHeight = 21.6.sp,
                        letterSpacing = (-0.18).sp,
                        color = Color(0xFF121212),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 20.dp,
                            bottom = 12.dp,
                        ),
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        items(
                            items = uiState.categories,
                            key = { it },
                        ) { category ->
                            HomeChip(
                                text = category,
                                selected = category == uiState.selectedCategory,
                                onClick = {
                                    onCategoryClick(category)
                                },
                            )
                        }
                    }
                }
            }

            items(
                items = uiState.products,
                key = { it.id },
            ) { product ->
                ProductCard(
                    product = product,
                    onFavoriteClick = { onFavoriteClick(product.id) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@PlayScreenPreviews
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        userName = "김솝트",
        uiState = HomeUiState(),
        onMenuClick = {},
        onCategoryClick = {},
        onFavoriteClick = {},
    )
}
