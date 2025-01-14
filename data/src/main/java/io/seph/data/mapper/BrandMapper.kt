package io.seph.data.mapper

import io.seph.data.entities.BrandDto
import io.seph.domain.model.BrandModel

internal class BrandMapper : Mapper<BrandDto, BrandModel> {
    override fun toDomainModel(entity: BrandDto): BrandModel {
        return entity.run {
            BrandModel(
                id = id,
                name = name
            )
        }
    }

    override fun toRaw(data: BrandModel): BrandDto {
        return data.run {
            BrandDto(
                id = id,
                name = name
            )
        }
    }
}