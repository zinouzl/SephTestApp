package io.seph.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BrandDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
) : BaseDto