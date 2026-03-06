package com.omar.sales.domain.usecase.product

import com.omar.sales.domain.model.Product
import com.omar.sales.domain.repository.ProductRepository

class CreateProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        val existing = repository.findProductByCode(product.code)

        require(existing == null) {
            "Product with code ${product.code} already exists"
        }

        repository.saveProduct(product)
    }
}