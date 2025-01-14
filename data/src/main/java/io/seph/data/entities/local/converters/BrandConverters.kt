package io.seph.data.entities.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import io.seph.data.entities.BrandDto
import io.seph.data.util.JsonParser
import kotlin.reflect.typeOf

@ProvidedTypeConverter
internal class BrandConverters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromBrandEntityJson(json: String): BrandDto {
        return jsonParser.fromJson<BrandDto>(
            json,
            typeOf<BrandDto>()
        )
    }

    @TypeConverter
    fun toBrandEntityJson(brandDto: BrandDto): String {
        return jsonParser.toJson(
            brandDto,
            typeOf<BrandDto>()
        )
    }
}