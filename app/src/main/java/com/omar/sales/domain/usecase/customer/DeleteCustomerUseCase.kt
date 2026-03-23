package com.omar.sales.domain.usecase.customer

import com.omar.sales.domain.repository.CustomerRepository

class DeleteCustomerUseCase(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(customerCode: String) {
        repository.deleteCustomer(customerCode)
    }
}