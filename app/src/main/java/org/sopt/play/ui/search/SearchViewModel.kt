package org.sopt.play.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.data.LocalStorage
import org.sopt.play.data.api.SearchApiFactory
import org.sopt.play.data.repository.SearchRepository
import org.sopt.play.ui.search.model.Post

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val localStorage: LocalStorage,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())

    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null,
                )
            }

            searchRepository.getPosts()
                .onSuccess { posts ->
                    _state.update { state ->
                        state.copy(
                            posts = posts.map { post ->
                                Post(
                                    id = post.id,
                                    author = post.author,
                                    date = post.createdAt
                                        .substringBefore("T")
                                        .replace("-", "."),
                                    title = post.title,
                                    content = post.content,
                                )
                            },
                            isLoading = false,
                        )
                    }
                }
                .onFailure {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = "게시글 목록을 불러오지 못했어요.",
                        )
                    }
                }
        }
    }

    private var isCreatingPost = false

    fun createPost(title: String, content: String) {
        if (isCreatingPost) return

        val authorizationCode = localStorage.getAuthorizationCode()
        val author = localStorage.getUserName()

        if (authorizationCode.isBlank() || author.isBlank()) {
            _state.update { state ->
                state.copy(errorMessage = "다시 로그인해주세요.")
            }
            return
        }

        isCreatingPost = true

        _state.update { state ->
            state.copy(errorMessage = null)
        }

        viewModelScope.launch {
            try {
                searchRepository.createPost(
                    authorizationCode = authorizationCode,
                    title = title,
                    content = content,
                    author = author,
                )
                    .onSuccess {
                        loadPosts()
                    }
                    .onFailure {
                        _state.update { state ->
                            state.copy(
                                errorMessage = "게시글을 등록하지 못했어요.",
                            )
                        }
                    }
            } finally {
                isCreatingPost = false
            }
        }
    }

    private var isDeletingPost = false

    fun deletePost(postId: Long) {
        if (isDeletingPost) return

        val authorizationCode = localStorage.getAuthorizationCode()

        if (authorizationCode.isBlank()) {
            _state.update { state ->
                state.copy(errorMessage = "다시 로그인해주세요.")
            }
            return
        }

        isDeletingPost = true

        _state.update { state ->
            state.copy(errorMessage = null)
        }

        viewModelScope.launch {
            try {
                searchRepository.deletePost(
                    authorizationCode = authorizationCode,
                    postId = postId,
                )
                    .onSuccess {
                        loadPosts()
                    }
                    .onFailure {
                        _state.update { state ->
                            state.copy(
                                errorMessage = "게시글을 삭제하지 못했어요.",
                            )
                        }
                    }
            } finally {
                isDeletingPost = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])

                SearchViewModel(
                    searchRepository = SearchRepository(
                        searchService = SearchApiFactory.searchService,
                    ),
                    localStorage = LocalStorage(application),
                )
            }
        }
    }
}