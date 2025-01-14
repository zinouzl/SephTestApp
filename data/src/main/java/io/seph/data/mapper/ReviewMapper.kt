package io.seph.data.mapper

import io.seph.data.entities.ReviewDto
import io.seph.domain.model.ReviewModel

internal class ReviewMapper : Mapper<ReviewDto, ReviewModel> {
    override fun toDomainModel(entity: ReviewDto): ReviewModel {
        return entity.run {
            ReviewModel(
                reviewerName = reviewerName,
                reviewText = reviewText,
                reviewRating = reviewRating
            )
        }
    }

    override fun toRaw(data: ReviewModel): ReviewDto {
        return data.run {
            ReviewDto(
                reviewerName = reviewerName,
                reviewText = reviewText,
                reviewRating = reviewRating
            )
        }
    }
}