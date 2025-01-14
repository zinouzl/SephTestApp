package io.seph.data.entities.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import io.seph.data.entities.ReviewDto
import io.seph.data.util.JsonParser
import kotlin.reflect.typeOf

@ProvidedTypeConverter
internal class ReviewsConverters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromReviewEntityJson(json: String): List<ReviewDto> {
        return jsonParser.fromJson<List<ReviewDto>>(
            json,
            typeOf<List<ReviewDto>>()
        )
    }

    @TypeConverter
    fun toReviewEntityJson(reviews: List<ReviewDto>): String {
        return jsonParser.toJson(
            reviews,
            typeOf<List<ReviewDto>>()
        )
    }
}