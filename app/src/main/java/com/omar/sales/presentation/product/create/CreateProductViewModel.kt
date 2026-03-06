package com.omar.sales.presentation.product.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.sales.domain.model.Product
import com.omar.sales.domain.usecase.product.CreateProductUseCase
import com.omar.sales.domain.validation.ProductValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateProductViewModel(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val validator = ProductValidator()

    private val _state = MutableStateFlow(CreateProductUiState())
    val state: StateFlow<CreateProductUiState> = _state

    private val _effect = Channel<CreateProductUiEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: CreateProductUiEvent) {
        when (event) {
            is CreateProductUiEvent.CodeChanged ->
                _state.update { it.copy(code = event.value) }

            is CreateProductUiEvent.DescriptionChanged ->
                _state.update { it.copy(description = event.value) }

            is CreateProductUiEvent.CategoryChanged ->
                _state.update { it.copy(category = event.value) }

            is CreateProductUiEvent.PriceChanged ->
                _state.update { it.copy(priceText = event.value) }

            is CreateProductUiEvent.StockChanged ->
                _state.update { it.copy(stockText = event.value) }

            is CreateProductUiEvent.TaxableChanged ->
                _state.update { it.copy(taxable = event.value) }

            CreateProductUiEvent.SaveClicked ->
                saveProduct()
        }
    }

    private fun saveProduct() {
        val currentState = state.value

        val validation = validator.validateAll(
            code = currentState.code,
            description = currentState.description,
            category = currentState.category,
            priceText = currentState.priceText,
            stockText = currentState.stockText
        )

        if (!validation.successful) {
            sendEffect(CreateProductUiEffect.ShowMessage(validation.errorMessage ?: "Validation error"))
            return
        }

        val price = currentState.priceText.toDouble()
        val stock = currentState.stockText.toInt()

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val product = Product(
                    code = currentState.code.trim(),
                    description = currentState.description.trim(),
                    category = currentState.category.trim(),
                    price = price,
                    stock = stock,
                    taxable = currentState.taxable
                )

                createProductUseCase(product)

                sendEffect(CreateProductUiEffect.ShowMessage("Product created"))
                sendEffect(CreateProductUiEffect.NavigateBack)

            } catch (e: Exception) {
                sendEffect(CreateProductUiEffect.ShowMessage(e.message ?: "Unknown error"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateProductUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}