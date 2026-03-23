package com.omar.sales.domain.model

data class Product(
    val code: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val taxable: Boolean = true
)