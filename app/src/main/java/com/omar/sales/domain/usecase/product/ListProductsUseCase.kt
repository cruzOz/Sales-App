package com.omar.sales.domain.usecase.product

import com.omar.sales.domain.model.Product
import com.omar.sales.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ListProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getProducts()
    }
}