package org.sopt.play.ui.home

import org.sopt.play.R

internal val homeMenus = listOf(
    "2배적립", "홈", "셀렉티드", "브랜드", "뷰티", "라이프", "랭킹",
)

internal val homeCategories = listOf(
    "전체", "상의", "팬츠", "원피스", "아우터",
    "스커트", "슈즈", "가방", "이너웨어/잠옷", "폰꾸미기",
)

internal val homeBanners = listOf(
    R.drawable.img_banner_1,
    R.drawable.img_banner_2,
    R.drawable.img_banner_3,
)

internal val homeProducts = listOf(
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
    val menus: List<String> = homeMenus,
    val categories: List<String> = homeCategories,
    val banners: List<Int> = homeBanners,
    val products: List<Product> = homeProducts,
    val selectedMenu: String = "홈",
    val selectedCategory: String = "전체",
)
