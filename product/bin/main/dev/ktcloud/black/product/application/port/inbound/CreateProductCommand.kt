package dev.ktcloud.black.product.application.port.inbound

interface CreateProductCommand {
    fun createProduct(command: In)

    data class In(
        val name: String,
        val description: String,
        val price: Int,
    )
}