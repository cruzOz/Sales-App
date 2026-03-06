package com.omar.sales.domain.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)