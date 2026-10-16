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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import org.sopt.play.ui.preview.PlayScreenPreviews
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.home.component.HomeBanner
import org.sopt.play.ui.home.component.HomeChip
import org.sopt.play.ui.home.component.HomeTab
import org.sopt.play.ui.home.component.HomeTopBar
import org.sopt.play.ui.home.component.ProductCard

private val homeMenus = listOf(
    "2배적립", "홈", "셀렉티드", "브랜드", "뷰티", "라이프", "랭킹",
)

private val homeCategories = listOf(
    "전체", "상의", "팬츠", "원피스", "아우터",
    "스커트", "슈즈", "가방", "이너웨어/잠옷", "폰꾸미기",
)

private val homeBanners = listOf(
    R.drawable.img_banner_1,
    R.drawable.img_banner_2,
    R.drawable.img_banner_3,
)

private val homeProducts = listOf(
    Product(
        id = 1,
        imageRes = R.drawable.img_product_empty,
        brand = "트랜디어패럴",
        name = "[카프리1위/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
        discountRate = 36,
        price = 28800,
    ),
    Product(
        id = 2,
        imageRes = R.drawable.img_product_empty,
        brand = "슬로우앤드",
        name = "[가을/3기장선택!] 모먼트 투커버 올데이슬랙스",
        discountRate = 50,
        price = 26820,
    ),
    Product(
        id = 3,
        imageRes = R.drawable.img_product_empty,
        brand = "어텀",
        name = "보울7부티셔츠",
        discountRate = 10,
        price = 17100,
    ),
    Product(
        id = 4,
        imageRes = R.drawable.img_product_empty,
        brand = "슬로우앤드",
        name = "[오픈할인중] 도브 클래식 탄탄핏 무지셔츠",
        discountRate = 40,
        price = 35200,
    ),
)

@Composable
fun HomeScreen(
    userName: String,
    modifier: Modifier = Modifier,
) {
    var selectedMenu by rememberSaveable {
        mutableStateOf("홈")
    }

    var selectedCategory by rememberSaveable {
        mutableStateOf("전체")
    }

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
                            items = homeMenus,
                            key = { it },
                        ) { menu ->
                            HomeTab(
                                text = menu,
                                selected = menu == selectedMenu,
                                onClick = {
                                    selectedMenu = menu
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
                            items = homeBanners,
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
                            items = homeCategories,
                            key = { it },
                        ) { category ->
                            HomeChip(
                                text = category,
                                selected = category == selectedCategory,
                                onClick = {
                                    selectedCategory = category
                                },
                            )
                        }
                    }
                }
            }

            items(
                items = homeProducts,
                key = { it.id },
            ) { product ->
                ProductCard(
                    product = product,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@PlayScreenPreviews
@Composable
private fun HomeScreenPreview() {
    HomeScreen(userName = "김솝트")
}