package io.seph.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductDto(
    @SerialName("product_id") val productId: Long,
    @SerialName("product_name") val productName: String,
    @SerialName("description") val description: String,
    @SerialName("price") val productPrice: Float,
    @SerialName("images_url") val productImages: ImagesUrlsDto,
    @SerialName("c_brand") val brand: BrandDto,
    @SerialName("is_productSet") val isProductSet: Boolean,
    @SerialName("is_special_brand") val isSpecialBrand: Boolean
) : BaseDto