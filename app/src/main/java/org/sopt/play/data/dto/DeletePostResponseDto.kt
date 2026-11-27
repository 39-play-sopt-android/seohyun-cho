package org.sopt.play.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeletePostResponseDto(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
)