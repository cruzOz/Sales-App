package com.omar.sales.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.sales.domain.usecase.product.DeleteProductUseCase
import com.omar.sales.domain.usecase.product.ListProductsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListProductViewModel(
    getProductsUseCase: ListProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    val uiState: StateFlow<ListProductUiState> =
        getProductsUseCase()
            .map { products ->
                ListProductUiState(
                    isLoading = false,
                    products = products
                )
            }
            .onStart {
                emit(ListProductUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListProductUiState()
            )

    fun deleteProduct(code: String) {
        viewModelScope.launch {
            deleteProductUseCase(code)
            _message.emit("Product deleted")
        }
    }
}