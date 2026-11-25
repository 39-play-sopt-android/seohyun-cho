package org.sopt.play.ui.home

import org.sopt.play.R
import org.sopt.play.ui.home.model.HomeCategory
import org.sopt.play.ui.home.model.HomeMenu

private val homeBanners = listOf(
    R.drawable.img_banner_1,
    R.drawable.img_banner_2,
    R.drawable.img_banner_3,
)

private val homeProducts = listOf(
    Product(
        id = 1,
        imageRes = R.drawable.img_product_1,
        brand = "트랜디어패럴",
        name = "[카프리1위/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
        discountRate = 36,
        price = 28800,
    ),
    Product(
        id = 2,
        imageRes = R.drawable.img_product_2,
        brand = "슬로우앤드",
        name = "[가을/3기장선택!] 모먼트 투커버 올데이슬랙스",
        discountRate = 50,
        price = 26820,
    ),
    Product(
        id = 3,
        imageRes = R.drawable.img_product_3,
        brand = "어텀",
        name = "보울7부티셔츠",
        discountRate = 10,
        price = 17100,
    ),
    Product(
        id = 4,
        imageRes = R.drawable.img_product_4,
        brand = "슬로우앤드",
        name = "[오픈할인중] 도브 클래식 탄탄핏 무지셔츠",
        discountRate = 40,
        price = 35200,
    ),
)

data class HomeUiState(
    val menus: List<HomeMenu> = HomeMenu.entries.toList(),
    val categories: List<HomeCategory> = HomeCategory.entries.toList(),
    val banners: List<Int> = homeBanners,
    val products: List<Product> = homeProducts,
    val selectedMenu: HomeMenu = HomeMenu.HOME,
    val selectedCategory: HomeCategory = HomeCategory.ALL,
)
