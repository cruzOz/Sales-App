package com.omar.sales.presentation.product.create

data class CreateProductUiState(
    val code: String = "",
    val description: String = "",
    val category: String = "",
    val priceText: String = "",
    val stockText: String = "",
    val taxable: Boolean = true,
    val isLoading: Boolean = false
)