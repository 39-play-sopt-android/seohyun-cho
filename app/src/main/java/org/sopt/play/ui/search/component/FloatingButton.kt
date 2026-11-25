package org.sopt.play.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R

@Composable
fun FloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val Red = Color(0xFFFF4D4D)
    Box(
        modifier = modifier
            .size(60.dp)
            .shadow(
                elevation = 12.dp,
                spotColor = Color(0x26000000),
                ambientColor = Color(0x26000000)
            )
            .clip(CircleShape)
            .background(Red)
            .clickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_plus_28),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FloatingButtonPreview() {
    FloatingButton(
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}