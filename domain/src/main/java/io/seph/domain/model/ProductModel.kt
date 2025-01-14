package io.seph.domain.model

data class ProductModel(
    val productId: Long,
    val productName: String,
    val description: String,
    val productPrice: Float,
    val productImage: String,
    val brand: BrandModel,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean
) : DomainModel