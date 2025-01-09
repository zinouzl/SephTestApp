package io.seph.data.entities

import kotlinx.serialization.SerialName

internal data class ProductsEntity(
    @SerialName("product_id") val productId: Long,
    @SerialName("product_name") val productName: String,
    @SerialName("price") val productPrice: Float,
    @SerialName("images_url") val productImage: String,
    @SerialName("c_brand") val brand: BrandEntity,
) : DataEntity