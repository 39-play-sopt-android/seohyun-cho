package org.sopt.play.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostResponseDto(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val data: PostDto? = null,
)