package org.sopt.play.ui.home

import androidx.annotation.DrawableRes

data class Product(
    val id: Int,
    @DrawableRes val imageRes: Int,
    val brand: String,
    val name: String,
    val discountRate: Int,
    val price: Int,
    val isFavorite: Boolean = false,
)