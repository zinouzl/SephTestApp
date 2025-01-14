package io.seph.data.entities.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import io.seph.data.entities.ImagesUrlsDto
import io.seph.data.util.JsonParser
import kotlin.reflect.typeOf

@ProvidedTypeConverter
internal class ImagesUrlsConverters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromImagesUrlsJson(json: String): ImagesUrlsDto {
        return jsonParser.fromJson<ImagesUrlsDto>(
            json,
            typeOf<ImagesUrlsDto>()
        )
    }

    @TypeConverter
    fun toImagesUrlsJson(reviews: ImagesUrlsDto): String {
        return jsonParser.toJson(
            reviews,
            typeOf<ImagesUrlsDto>()
        )
    }
}