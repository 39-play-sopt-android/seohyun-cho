package org.sopt.play.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequestDto(
    val title: String,
    val content: String,
    val author: String,
)