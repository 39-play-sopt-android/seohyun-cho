package org.sopt.play.ui.home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeViewModelTest {
    @Test
    fun favoriteChangesOnlyTheClickedProductAndCanBeCancelled() {
        val viewModel = HomeViewModel()
        val original = viewModel.uiState.value.products
        val productId = original.first().id

        viewModel.onFavoriteClick(productId)

        assertTrue(viewModel.uiState.value.products.first().isFavorite)
        assertEquals(original.drop(1), viewModel.uiState.value.products.drop(1))
        assertFalse(original.first().isFavorite)

        viewModel.onFavoriteClick(productId)
        assertEquals(original, viewModel.uiState.value.products)
    }

    @Test
    fun changingMenuAndCategoryPreservesFavorites() {
        val viewModel = HomeViewModel()
        val productId = viewModel.uiState.value.products.first().id
        viewModel.onFavoriteClick(productId)

        viewModel.onMenuClick("뷰티")
        viewModel.onCategoryClick("상의")

        assertEquals("뷰티", viewModel.uiState.value.selectedMenu)
        assertEquals("상의", viewModel.uiState.value.selectedCategory)
        assertTrue(viewModel.uiState.value.products.first().isFavorite)
    }
}
