package com.omar.sales.presentation.customer.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omar.sales.domain.model.Customer

@Composable
fun CustomerItem(
    customer: Customer,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = customer.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text("Código: ${customer.code}")
            Text("Correo: ${customer.email}")
            Text("Teléfono: ${customer.phone}")
            Text("Dirección: ${customer.address}")

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onDelete(customer.code) }
            ) {
                Text("Eliminar")
            }
        }
    }
}