package io.seph.data.entities

import kotlinx.serialization.SerialName

internal data class ReviewEntity(
    @SerialName("name") val reviewerName: String?,
    @SerialName("text") val reviewText: String,
    @SerialName("rating") val reviewRating: Int
) : DataEntity