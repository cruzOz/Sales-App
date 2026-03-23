package com.omar.sales.presentation.customer.create

sealed interface CreateCustomerUiEffect {
    data class ShowMessage(val message: String) : CreateCustomerUiEffect
    data object NavigateBack : CreateCustomerUiEffect
}