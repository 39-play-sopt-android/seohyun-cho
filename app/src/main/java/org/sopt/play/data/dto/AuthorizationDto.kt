package org.sopt.play.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationRequestDto(
    val part: String,
)

@Serializable
data class AuthorizationResponseDto(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val data: AuthorizationDataDto,
)

@Serializable
data class AuthorizationDataDto(
    val authorizationCode: String,
)