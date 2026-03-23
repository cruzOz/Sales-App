package com.omar.sales.presentation.customer.create

data class CreateCustomerUiState(
    val code: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val isLoading: Boolean = false
)