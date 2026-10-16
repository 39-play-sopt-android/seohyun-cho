package org.sopt.play.ui.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R

@Composable
fun HomeBanner(
    @DrawableRes imageRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(328f / 195f),
    )
}

@Preview(
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun HomeBannerPreview() {
    HomeBanner(
        imageRes = R.drawable.img_banner_1,
        contentDescription = "첫 번째 기획전 배너",
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
    )
}