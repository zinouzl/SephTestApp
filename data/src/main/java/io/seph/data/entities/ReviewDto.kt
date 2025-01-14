package io.seph.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ReviewDto(
    @SerialName("name") val reviewerName: String? = null,
    @SerialName("text") val reviewText: String? = null,
    @SerialName("rating") val reviewRating: Float? = null
) : BaseDto