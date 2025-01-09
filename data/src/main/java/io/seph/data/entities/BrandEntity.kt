package io.seph.data.entities

import kotlinx.serialization.SerialName

internal data class BrandEntity(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
) : DataEntity