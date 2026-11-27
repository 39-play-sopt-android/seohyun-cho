package org.sopt.play.ui.search

import org.sopt.play.ui.search.model.Post

data class SearchUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)