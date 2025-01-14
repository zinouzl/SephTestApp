package io.seph.data.mapper

import io.seph.data.entities.BaseDto
import io.seph.domain.model.DomainModel

/**
 * An interface for mapping between a data layer entity ([BaseDto]) and its corresponding domain layer object ([DomainModel]).
 *
 * This interface defines two core mapping functions:
 *  - `toData`: Converts a data layer entity to its domain layer object representation.
 *  - `toRaw`: Converts domain layer object back to its data layer entity.
 *
 *
 * @param E The type of the data entity. Must inherit from [BaseDto].
 * @param D The type of the domain data. Must inherit from [DomainModel].
 */
internal interface Mapper<E : BaseDto, D : DomainModel> {

    fun toDomainModel(entity: E): D

    fun toRaw(data: D): E
}