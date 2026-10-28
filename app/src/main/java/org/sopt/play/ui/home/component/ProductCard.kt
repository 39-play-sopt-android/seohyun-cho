package org.sopt.play.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.ui.home.Product
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
) {
    val mediumStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = (-0.14).sp,
        color = Color(0xFF121212),
    )

    Column(modifier = modifier) {
        Box {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f),
            )
            Image(
                painter = painterResource(
                    if (product.isFavorite) R.drawable.favorite_fill else R.drawable.favorite,
                ),
                contentDescription = if (product.isFavorite) "좋아요 선택됨" else "좋아요 선택 안 됨",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(24.dp),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 12.dp,
                    bottom = 20.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = product.brand,
                style = mediumStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = product.name,
                style = mediumStyle.copy(
                    fontFamily = FontFamily(
                        Font(R.font.pretendard_regular)
                    ),
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "${product.discountRate}%",
                    style = mediumStyle,
                    color = Color(0xFFFF4D4D),
                )

                Text(
                    text = NumberFormat
                        .getNumberInstance(Locale.KOREA)
                        .format(product.price),
                    style = mediumStyle,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    fontScale = 1f,
)
@Composable
private fun ProductCardPreview() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ProductCard(
            product = Product(
                id = 1,
                imageRes = R.drawable.img_product_empty,
                brand = "만약에 이 브랜드명이 엄청 길다면 넌 어떻게 할건데",
                name = "만약에 이 상품명이 엄청 길다면 넌 어떻게 할건데",
                discountRate = 36,
                price = 28800,
            ),
            modifier = Modifier.width(160.dp),
        )

        ProductCard(
            product = Product(
                id = 2,
                isFavorite = true,
                imageRes = R.drawable.img_product_empty,
                brand = "어텀",
                name = "보울7부티셔츠",
                discountRate = 10,
                price = 17100,
            ),
            modifier = Modifier.width(160.dp),
        )
    }
}
