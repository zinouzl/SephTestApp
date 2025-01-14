package io.seph.presentation.mapper

import io.seph.domain.model.BrandModel
import io.seph.presentation.models.Brand

class BrandMapper : UiMapper<BrandModel, Brand> {
    override fun toUiModel(model: BrandModel): Brand {
        return model.run {
            Brand(
                id = id,
                name = name
            )
        }
    }
}