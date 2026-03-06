package com.omar.sales.presentation.product.create

sealed interface CreateProductUiEffect {
    data class ShowMessage(val message: String) : CreateProductUiEffect
    data object NavigateBack : CreateProductUiEffect
}