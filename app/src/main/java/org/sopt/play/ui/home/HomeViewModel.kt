package org.sopt.play.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.sopt.play.ui.home.model.HomeCategory
import org.sopt.play.ui.home.model.HomeMenu

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onMenuClick(menu: HomeMenu) {
        _uiState.update { state ->
            if (menu in state.menus) {
                state.copy(selectedMenu = menu)
            } else {
                state
            }
        }
    }

    fun onCategoryClick(category: HomeCategory) {
        _uiState.update { state ->
            if (category in state.categories) {
                state.copy(selectedCategory = category)
            } else {
                state
            }
        }
    }

    fun onFavoriteClick(productId: Int) {
        _uiState.update { state ->
            state.copy(products = state.products.map { product ->
                if (product.id == productId) {
                    product.copy(isFavorite = !product.isFavorite)
                } else {
                    product
                }
            })
        }
    }
}
