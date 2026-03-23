package com.omar.sales.presentation.customer.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.sales.domain.usecase.customer.DeleteCustomerUseCase
import com.omar.sales.domain.usecase.customer.ListCustomersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListCustomerViewModel(
    getCustomersUseCase: ListCustomersUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase
) : ViewModel() {

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    val uiState: StateFlow<ListCustomerUiState> =
        getCustomersUseCase()
            .map { customers ->
                ListCustomerUiState(
                    isLoading = false,
                    customers = customers
                )
            }
            .onStart {
                emit(ListCustomerUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListCustomerUiState()
            )

    fun deleteCustomer(code: String) {
        viewModelScope.launch {
            deleteCustomerUseCase(code)
            _message.emit("Cliente eliminado")
        }
    }
}