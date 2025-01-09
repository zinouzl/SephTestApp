package io.seph.data.entities

import kotlinx.serialization.SerialName

internal data class ProductReviewEntity(
    @SerialName("product_id") val productId: Long,
    @SerialName("reviews") val reviews: List<ReviewEntity>
) : DataEntity