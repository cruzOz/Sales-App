package com.omar.sales.presentation.customer.list

import com.omar.sales.domain.model.Customer

data class ListCustomerUiState(
    val isLoading: Boolean = false,
    val customers: List<Customer> = emptyList()
)