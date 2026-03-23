package com.omar.sales.presentation.customer.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCustomerScreen(
    viewModel: CreateCustomerViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CreateCustomerUiEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                CreateCustomerUiEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Crear Cliente") }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            TextField(
                value = state.code,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.CodeChanged(it)) },
                label = { Text("Código") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.NameChanged(it)) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.EmailChanged(it)) },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.phone,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.PhoneChanged(it)) },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.address,
                onValueChange = { viewModel.onEvent(CreateCustomerUiEvent.AddressChanged(it)) },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.onEvent(CreateCustomerUiEvent.SaveClicked) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}