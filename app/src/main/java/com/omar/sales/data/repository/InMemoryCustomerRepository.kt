package com.omar.sales.data.repository

import com.omar.sales.domain.model.Customer
import com.omar.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class InMemoryCustomerRepository :
    BaseInMemoryRepository<Customer, String>(
        initialData = listOf(
            Customer("C1", "Juan Pérez", "juan@gmail.com", "9511111111", "Oaxaca"),
            Customer("C2", "María López", "maria@gmail.com", "9512222222", "Zaachila")
        )
    ),
    CustomerRepository {

    override fun getId(item: Customer): String = item.code

    override suspend fun saveCustomer(customer: Customer) {
        save(customer)
    }

    override suspend fun deleteCustomer(customerCode: String) {
        deleteById(customerCode)
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return findById(customerCode)
    }

    override fun getCustomers(): Flow<List<Customer>> = observeAll()
}