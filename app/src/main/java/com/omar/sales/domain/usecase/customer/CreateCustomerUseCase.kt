package com.omar.sales.domain.usecase.customer

import com.omar.sales.domain.model.Customer
import com.omar.sales.domain.repository.CustomerRepository

class CreateCustomerUseCase(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(customer: Customer) {
        val existing = repository.findCustomerByCode(customer.code)

        require(existing == null) {
            "Customer with code ${customer.code} already exists"
        }

        repository.saveCustomer(customer)
    }
}