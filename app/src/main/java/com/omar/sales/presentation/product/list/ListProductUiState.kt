package com.omar.sales.presentation.product.list

import com.omar.sales.domain.model.Product

data class ListProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)