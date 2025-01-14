package io.seph.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductReviewDto(
    @SerialName("product_id") val productId: Long,
    @SerialName("reviews") val reviews: List<ReviewDto>
) : BaseDto