package com.omar.sales.presentation.customer.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.sales.domain.model.Customer
import com.omar.sales.domain.usecase.customer.CreateCustomerUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCustomerViewModel(
    private val createCustomerUseCase: CreateCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCustomerUiState())
    val state: StateFlow<CreateCustomerUiState> = _state

    private val _effect = Channel<CreateCustomerUiEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: CreateCustomerUiEvent) {
        when (event) {
            is CreateCustomerUiEvent.CodeChanged ->
                _state.update { it.copy(code = event.value) }

            is CreateCustomerUiEvent.NameChanged ->
                _state.update { it.copy(name = event.value) }

            is CreateCustomerUiEvent.EmailChanged ->
                _state.update { it.copy(email = event.value) }

            is CreateCustomerUiEvent.PhoneChanged ->
                _state.update { it.copy(phone = event.value) }

            is CreateCustomerUiEvent.AddressChanged ->
                _state.update { it.copy(address = event.value) }

            CreateCustomerUiEvent.SaveClicked ->
                saveCustomer()
        }
    }

    private fun saveCustomer() {
        val currentState = state.value

        if (currentState.code.isBlank()) {
            sendEffect(CreateCustomerUiEffect.ShowMessage("Código requerido"))
            return
        }

        if (currentState.name.isBlank()) {
            sendEffect(CreateCustomerUiEffect.ShowMessage("Nombre requerido"))
            return
        }

        if (currentState.email.isBlank()) {
            sendEffect(CreateCustomerUiEffect.ShowMessage("Correo requerido"))
            return
        }

        if (currentState.phone.isBlank()) {
            sendEffect(CreateCustomerUiEffect.ShowMessage("Teléfono requerido"))
            return
        }

        if (currentState.address.isBlank()) {
            sendEffect(CreateCustomerUiEffect.ShowMessage("Dirección requerida"))
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val customer = Customer(
                    code = currentState.code.trim(),
                    name = currentState.name.trim(),
                    email = currentState.email.trim(),
                    phone = currentState.phone.trim(),
                    address = currentState.address.trim()
                )

                createCustomerUseCase(customer)

                sendEffect(CreateCustomerUiEffect.ShowMessage("Cliente creado"))
                sendEffect(CreateCustomerUiEffect.NavigateBack)

            } catch (e: Exception) {
                sendEffect(CreateCustomerUiEffect.ShowMessage(e.message ?: "Error desconocido"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateCustomerUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}