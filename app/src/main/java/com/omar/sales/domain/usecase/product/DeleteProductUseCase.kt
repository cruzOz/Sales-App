package com.omar.sales.domain.usecase.product

import com.omar.sales.domain.repository.ProductRepository

class DeleteProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productCode: String) {
        repository.deleteProduct(productCode)
    }
}