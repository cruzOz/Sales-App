package com.omar.sales.domain.validation

class ProductValidator {

    fun validateCode(code: String): ValidationResult {
        if (code.isBlank()) return ValidationResult(false, "Code required")
        return ValidationResult(true)
    }

    fun validateDescription(description: String): ValidationResult {
        if (description.isBlank()) return ValidationResult(false, "Description required")
        return ValidationResult(true)
    }

    fun validateCategory(category: String): ValidationResult {
        if (category.isBlank()) return ValidationResult(false, "Category required")
        return ValidationResult(true)
    }

    fun validatePrice(priceText: String): ValidationResult {
        val price = priceText.toDoubleOrNull()
            ?: return ValidationResult(false, "Invalid price")

        if (price <= 0.0) return ValidationResult(false, "Price must be greater than 0")
        return ValidationResult(true)
    }

    fun validateStock(stockText: String): ValidationResult {
        val stock = stockText.toIntOrNull()
            ?: return ValidationResult(false, "Invalid stock")

        if (stock < 0) return ValidationResult(false, "Stock cannot be negative")
        return ValidationResult(true)
    }

    fun validateAll(
        code: String,
        description: String,
        category: String,
        priceText: String,
        stockText: String
    ): ValidationResult {
        validateCode(code).also { if (!it.successful) return it }
        validateDescription(description).also { if (!it.successful) return it }
        validateCategory(category).also { if (!it.successful) return it }
        validatePrice(priceText).also { if (!it.successful) return it }
        validateStock(stockText).also { if (!it.successful) return it }

        return ValidationResult(true)
    }
}