package io.seph.data.mapper.local

import io.seph.data.entities.BaseDto
import io.seph.data.entities.local.LocalEntity

/**
 * An interface for mapping between a data entity [BaseDto] and a local entity [LocalEntity].
 *
 * This interface defines two core mapping functions:
 *  - `toDataEntity`: Transforms a local entity back into a data entity to restore cache.
 *  - `toLocalEntity`: Transforms a data entity into a local entity to store it in a database.
 *
 *
 * @param E The type of the local entity (database entity). Must inherit from [LocalEntity].
 * @param L The type of the data entity (remote entity). Must inherit from [BaseDto].
 */
internal interface LocalMapper<E : LocalEntity, L : BaseDto> {

    fun toEntity(local: E): L

    fun toLocalEntity(entity: L): E
}