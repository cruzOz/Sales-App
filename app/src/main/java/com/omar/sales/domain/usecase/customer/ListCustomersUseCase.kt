package com.omar.sales.domain.usecase.customer

import com.omar.sales.domain.model.Customer
import com.omar.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class ListCustomersUseCase(
    private val repository: CustomerRepository
) {
    operator fun invoke(): Flow<List<Customer>> {
        return repository.getCustomers()
    }
}