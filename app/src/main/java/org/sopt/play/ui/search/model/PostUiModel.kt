package org.sopt.play.ui.search.model

data class PostUiModel(
    val id: String,
    val author: String,
    val date: String,
    val title: String,
    val content: String,
)

data class Post(
    val id: Long,
    val author: String,
    val date: String,
    val title: String,
    val content: String,
)
