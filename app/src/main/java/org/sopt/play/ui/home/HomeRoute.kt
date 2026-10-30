package org.sopt.play.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.ui.main.MainScreen

@Composable
fun HomeRoute(
    userName: String,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        userName = userName,
        homeUiState = uiState,
        onMenuClick = viewModel::onMenuClick,
        onCategoryClick = viewModel::onCategoryClick,
        onFavoriteClick = viewModel::onFavoriteClick,
    )
}
