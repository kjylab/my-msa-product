package dev.ktcloud.black.product.domain.entity

data class ProductDomainEntity(
    val id: String = "",
    val name: String,
    val description: String,
    val price: Int,
)