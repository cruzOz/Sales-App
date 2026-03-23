package com.omar.sales.presentation.customer.create

sealed interface CreateCustomerUiEvent {
    data class CodeChanged(val value: String) : CreateCustomerUiEvent
    data class NameChanged(val value: String) : CreateCustomerUiEvent
    data class EmailChanged(val value: String) : CreateCustomerUiEvent
    data class PhoneChanged(val value: String) : CreateCustomerUiEvent
    data class AddressChanged(val value: String) : CreateCustomerUiEvent
    data object SaveClicked : CreateCustomerUiEvent
}