package io.seph.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ImagesUrlsDto(
    @SerialName("small") val small: String,
    @SerialName("large") val large: String,
) : BaseDto