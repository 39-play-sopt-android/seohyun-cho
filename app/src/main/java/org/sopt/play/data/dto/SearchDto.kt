package org.sopt.play.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val data: SearchDataDto,
)

@Serializable
data class SearchDataDto(
    val posts: List<PostDto>,
    val nextCursor: Long?,
    val hasNext: Boolean,
)

@Serializable
data class PostDto(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
    val updatedAt: String,
)