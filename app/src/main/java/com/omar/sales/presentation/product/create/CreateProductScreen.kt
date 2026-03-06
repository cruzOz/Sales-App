package com.omar.sales.presentation.product.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProductScreen(
    viewModel: CreateProductViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CreateProductUiEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                CreateProductUiEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Crear producto") }) },
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
                onValueChange = { viewModel.onEvent(CreateProductUiEvent.CodeChanged(it)) },
                label = { Text("Codigo") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.description,
                onValueChange = { viewModel.onEvent(CreateProductUiEvent.DescriptionChanged(it)) },
                label = { Text("Descripicion") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.category,
                onValueChange = { viewModel.onEvent(CreateProductUiEvent.CategoryChanged(it)) },
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.priceText,
                onValueChange = { viewModel.onEvent(CreateProductUiEvent.PriceChanged(it)) },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.stockText,
                onValueChange = { viewModel.onEvent(CreateProductUiEvent.StockChanged(it)) },
                label = { Text("Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Column {
                Text("Aplicar impuesto?")
                Switch(
                    checked = state.taxable,
                    onCheckedChange = { viewModel.onEvent(CreateProductUiEvent.TaxableChanged(it)) }
                )
            }

            Button(
                onClick = { viewModel.onEvent(CreateProductUiEvent.SaveClicked) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                } else {
                    Text("Save")
                }
            }
        }
    }
}