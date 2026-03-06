package com.omar.sales.data.repository

import com.omar.sales.domain.model.Product
import com.omar.sales.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class InMemoryProductRepository :
    BaseInMemoryRepository<Product, String>(
        initialData = listOf(
            Product("P1", "Laptop", "Electronics", 15000.0, 10, true),
            Product("P2", "Mouse", "Electronics", 500.0, 50, true),
            Product("P3", "Desk", "Furniture", 3000.0, 5, false)
        )
    ),
    ProductRepository {

    override fun getId(item: Product): String = item.code

    override suspend fun saveProduct(product: Product) {
        save(product)
    }

    override suspend fun deleteProduct(productCode: String) {
        deleteById(productCode)
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        return findById(productCode)
    }

    override fun getProducts(): Flow<List<Product>> = observeAll()
}